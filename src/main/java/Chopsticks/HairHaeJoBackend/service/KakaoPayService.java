package Chopsticks.HairHaeJoBackend.service;

import Chopsticks.HairHaeJoBackend.dto.Payment.*;

import Chopsticks.HairHaeJoBackend.entity.menu.DesignerMenu;
import Chopsticks.HairHaeJoBackend.entity.menu.DesignerMenuRepository;
import Chopsticks.HairHaeJoBackend.entity.order.Order;
import Chopsticks.HairHaeJoBackend.entity.order.OrderRepository;
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
    private OrderRepository orderRepository;
    @Autowired
    private DesignerMenuRepository designerMenuRepository;
    public KakaopayReadyResponse kakaoPayReady(Kakaopayrequest kakaopayrequest) {


        Optional<DesignerMenu> tempdesignerMenu =designerMenuRepository.findById(kakaopayrequest.getMenu_id());
        if(tempdesignerMenu.isEmpty()) {
            throw new RuntimeException("존재하지 않는 메뉴입니다");
        }
        DesignerMenu designerMenu=tempdesignerMenu.get();
        Order order=orderRepository.save(kakaopayrequest.toOrder(SecurityUtil.getCurrentMemberId(),"no",designerMenu.getMenuPrice(),0));

        // 카카오페이 요청 양식
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("partner_order_id",Integer.toString(order.getId()));
        parameters.add("partner_user_id", Long.toString(SecurityUtil.getCurrentMemberId()));
        parameters.add("item_name", designerMenu.getMenuName());
        parameters.add("item_code",Integer.toString(designerMenu.getMenuId()));
        parameters.add("quantity", "1");
        parameters.add("total_amount", Integer.toString(designerMenu.getMenuPrice()));
        parameters.add("tax_free_amount","0");
        /*
        parameters.add("approval_url", "http://localhost:8080/payment/success"); // 성공 시 redirect url
        parameters.add("cancel_url", "http://localhost:8080/payment/cancel"); // 취소 시 redirect url
        parameters.add("fail_url", "http://localhost:8080/payment/fail"); // 실패 시 redirect url
        */
        //https://hairhaejo.site/
        parameters.add("approval_url", "https://hairhaejo.site/payment/success"); // 성공 시 redirect url
        parameters.add("cancel_url", "http://hairhaejo.site/payment/cancel"); // 취소 시 redirect url
        parameters.add("fail_url", "http://hairhaejo.site/payment/fail"); // 실패 시 redirect url
        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        kakaoReady = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/ready",
                requestEntity,
                KakaopayReadyResponse.class);
        order.setTid(kakaoReady.getTid());
        orderRepository.save(order);
        return kakaoReady;


    }


    public KakaopayApproveResponse ApproveResponse(String pgToken) {

        // 카카오 요청
        Order order=orderRepository.findBytid(kakaoReady.getTid());
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("tid", kakaoReady.getTid());
        parameters.add("partner_order_id", Integer.toString(order.getId()));
        parameters.add("partner_user_id", Long.toString(order.getUserId()));
        parameters.add("pg_token", pgToken);

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();


        KakaopayApproveResponse approveResponse = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/approve",
                requestEntity,
                KakaopayApproveResponse.class);


        return approveResponse;
    }
    public KakaopayCancelResponse kakaoCancel(KakaopayCancelrequest kakaopayCancelrequest) {
        Optional<Order> temporder=orderRepository.findById(kakaopayCancelrequest.getOrder_id());
        if(temporder.isEmpty()) {
            throw new RuntimeException("존재하지 않는 메뉴입니다");
        }
        Order order=temporder.get();
        // 카카오페이 요청
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("tid", order.getTid());
        parameters.add("cancel_amount", Integer.toString(order.getTotalAmount()));
        parameters.add("cancel_tax_free_amount", Integer.toString(order.getTaxFree()));
        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());
        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();
        KakaopayCancelResponse cancelResponse = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/cancel",
                requestEntity,
                KakaopayCancelResponse.class);
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
