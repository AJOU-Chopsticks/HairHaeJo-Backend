package Chopsticks.HairHaeJoBackend.service;

import Chopsticks.HairHaeJoBackend.dto.Payment.*;

import Chopsticks.HairHaeJoBackend.entity.menu.DesignerMenu;
import Chopsticks.HairHaeJoBackend.entity.menu.DesignerMenuRepository;

import Chopsticks.HairHaeJoBackend.entity.reservation.Reservation;
import Chopsticks.HairHaeJoBackend.entity.reservation.ReservationRepository;
import Chopsticks.HairHaeJoBackend.entity.reservation.ReservationState;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class KakaoPayService {

    static final String cid = "TC0ONETIME";
    @Value("${kakao-admin-key}")
    private String adminKey;
    private KakaopayReadyResponse kakaoReady;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private DesignerMenuRepository designerMenuRepository;
    public KakaopayReadyResponse kakaoPayReady(Kakaopayrequest kakaopayrequest) {


        Optional<DesignerMenu> tempdesignerMenu =designerMenuRepository.findById(kakaopayrequest.getMenu_id());
        if(tempdesignerMenu.isEmpty()) {
            throw new RuntimeException("존재하지 않는 메뉴입니다");
        }
        DesignerMenu designerMenu=tempdesignerMenu.get();
        Reservation reservation=reservationRepository.save(kakaopayrequest.toReservation(SecurityUtil.getCurrentMemberId(),"x",(short)0));

        // 카카오페이 요청 양식
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("partner_order_id",Integer.toString(reservation.getId()));
        parameters.add("partner_user_id", Long.toString(SecurityUtil.getCurrentMemberId()));
        parameters.add("item_name", designerMenu.getMenuName());
        parameters.add("item_code",Integer.toString(designerMenu.getMenuId()));
        parameters.add("quantity", "1");
        parameters.add("total_amount", Integer.toString(designerMenu.getMenuPrice()));
        parameters.add("tax_free_amount",Integer.toString(designerMenu.getMenuPrice()/10));
/*
        parameters.add("approval_url", "http://localhost:8080/payment/success"); // 성공 시 redirect url
        parameters.add("cancel_url", "http://localhost:8080/payment/cancel"); // 취소 시 redirect url
        parameters.add("fail_url", "http://localhost:8080/payment/fail"); // 실패 시 redirect url
*/


        //https://hairhaejo.site/
        parameters.add("approval_url", "http://54.180.182.1:8080/reservation/result"); // 성공 시 redirect url
        parameters.add("cancel_url", "http://54.180.182.1:8080/payment/cancel"); // 취소 시 redirect url
        parameters.add("fail_url", "http://54.180.182.1:8080/payment/fail"); // 실패 시 redirect url

        // 파라미터, 헤더

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        kakaoReady = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/ready",
                requestEntity,
                KakaopayReadyResponse.class);
        reservation.setTid(kakaoReady.getTid());
        reservationRepository.save(reservation);
        return kakaoReady;


    }


    public KakaopayApproveResponse ApproveResponse(String pgToken,String tid) {

        // 카카오 요청
        Reservation reservation=reservationRepository.findBytid(tid);
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("tid", tid);
        parameters.add("partner_order_id", Integer.toString(reservation.getId()));
        parameters.add("partner_user_id", Long.toString(reservation.getClientId()));
        parameters.add("pg_token", pgToken);

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();


        KakaopayApproveResponse approveResponse = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/approve",
                requestEntity,
                KakaopayApproveResponse.class);
        reservation.setState((short)1);
        reservationRepository.save(reservation);
        return approveResponse;
    }
    public void deletereserve() {
        Reservation reservation=reservationRepository.findBytid(kakaoReady.getTid());
        reservationRepository.deleteById(reservation.getId());

    }
    public KakaopayCancelResponse kakaoCancel(KakaopayCancelrequest kakaopayCancelrequest) {
        Optional<Reservation> tempreservation=reservationRepository.findById(kakaopayCancelrequest.getReservation_id());
        if(tempreservation.isEmpty()) {
            throw new RuntimeException("존재하지 않는 예약입니다");
        }
        Reservation reservation=tempreservation.get();
        Optional<DesignerMenu> tempdesignerMenu =designerMenuRepository.findById(reservation.getMenuId());
        if(tempdesignerMenu.isEmpty()) {
            throw new RuntimeException("존재하지 않는 메뉴입니다");
        }
        DesignerMenu designerMenu=tempdesignerMenu.get();
        // 카카오페이 요청
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("tid", reservation.getTid());
        parameters.add("cancel_amount", Integer.toString(designerMenu.getMenuPrice()));
        parameters.add("cancel_tax_free_amount", Integer.toString(designerMenu.getMenuPrice()/10));
        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());
        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();
        KakaopayCancelResponse cancelResponse = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/cancel",
                requestEntity,
                KakaopayCancelResponse.class);
        reservation.setState((short)3);
        reservationRepository.save(reservation);
        return cancelResponse;
    }
    private  HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();

        String auth = "KakaoAK " + adminKey;
        httpHeaders.set("Authorization", auth);
        httpHeaders .add("Accept", "application/json");
        httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");


        return httpHeaders;
    }


}
