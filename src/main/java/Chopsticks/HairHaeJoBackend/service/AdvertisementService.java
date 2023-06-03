package Chopsticks.HairHaeJoBackend.service;

import Chopsticks.HairHaeJoBackend.dto.Advertisement.AdvertisementRequestDto;
import Chopsticks.HairHaeJoBackend.dto.Advertisement.AdvertisementResponseDto;
import Chopsticks.HairHaeJoBackend.dto.Advertisement.ChangeAdRequestDto;
import Chopsticks.HairHaeJoBackend.dto.Advertisement.MyAdvertisementResponseDto;
import Chopsticks.HairHaeJoBackend.dto.Payment.KakaopayApproveResponse;
import Chopsticks.HairHaeJoBackend.dto.Payment.KakaopayCancelResponse;
import Chopsticks.HairHaeJoBackend.dto.Payment.KakaopayReadyResponse;
import Chopsticks.HairHaeJoBackend.entity.advertisement.Advertisement;
import Chopsticks.HairHaeJoBackend.entity.advertisement.AdvertisementRepository;
import Chopsticks.HairHaeJoBackend.entity.designer.DesignerProfile;
import Chopsticks.HairHaeJoBackend.entity.designer.DesignerProfileRepository;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import Chopsticks.HairHaeJoBackend.entity.user.UserRepository;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class AdvertisementService {

    static final String cid = "TC0ONETIME";
    @Value("${kakao-admin-key}")
    private String adminKey;
    private KakaopayReadyResponse kakaoReady;
    private final AdvertisementRepository advertisementRepository;
    private final UserRepository userRepository;
    private final S3UploadService s3UploadService;
    private final DesignerProfileRepository designerProfileRepository;

    public KakaopayReadyResponse kakaoPayReady(MultipartFile image, AdvertisementRequestDto requestDto)
        throws IOException {
        Advertisement advertisement = advertisementRepository.save(requestDto.toAdvertisement(getCurrentUser(), s3UploadService.upload(image),"x"));

        // 카카오페이 요청 양식
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("partner_order_id",Long.toString(advertisement.getAdvertiseId()));
        parameters.add("partner_user_id", Long.toString(advertisement.getAdvertiserId().getId()));
        parameters.add("item_name", "광고비");
        parameters.add("item_code",Long.toString(advertisement.getAdvertiseId()));
        parameters.add("quantity", "1");
        parameters.add("total_amount", Integer.toString(advertisement.getAdPrice()));
        parameters.add("tax_free_amount",Integer.toString(advertisement.getAdPrice()/10));

        //https://hairhaejo.site/
        parameters.add("approval_url", "https://hairhaejo.site/ad/result"); // 성공 시 redirect url
        parameters.add("cancel_url", "http://54.180.182.1:8080/ad/cancel"); // 취소 시 redirect url
        parameters.add("fail_url", "http://54.180.182.1:8080/ad/fail"); // 실패 시 redirect url

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        kakaoReady = restTemplate.postForObject(
            "https://kapi.kakao.com/v1/payment/ready",
            requestEntity,
            KakaopayReadyResponse.class);
        advertisement.setTid(kakaoReady.getTid());
        advertisementRepository.save(advertisement);

        return kakaoReady;
    }


    public KakaopayApproveResponse approveResponse(String pgToken,String tid) {

        // 카카오 요청
        Advertisement advertisement = advertisementRepository.findByTid(tid);
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("tid", tid);
        parameters.add("partner_order_id", Long.toString(advertisement.getAdvertiseId()));
        parameters.add("partner_user_id", Long.toString(advertisement.getAdvertiserId().getId()));
        parameters.add("pg_token", pgToken);
        System.out.println("test2");
        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        KakaopayApproveResponse approveResponse = restTemplate.postForObject(
            "https://kapi.kakao.com/v1/payment/approve",
            requestEntity,
            KakaopayApproveResponse.class);
        advertisement.setState(1);
        advertisementRepository.save(advertisement);

        return approveResponse;
    }

    public KakaopayCancelResponse kakaoCancel(Long advertiseId) {
        Advertisement advertisement = advertisementRepository.findById(advertiseId)
            .orElseThrow(() -> new RuntimeException("존재하지 않은 광고입니다."));
        if(advertisement.getState() == 2) throw new RuntimeException("이미 승인된 광고입니다.");

        // 카카오페이 요청
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("tid", advertisement.getTid());
        parameters.add("cancel_amount", Integer.toString(advertisement.getAdPrice()));
        parameters.add("cancel_tax_free_amount", Integer.toString(advertisement.getAdPrice()/10));

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();
        KakaopayCancelResponse cancelResponse = restTemplate.postForObject(
            "https://kapi.kakao.com/v1/payment/cancel",
            requestEntity,
            KakaopayCancelResponse.class);
        advertisement.setState(3);
        advertisementRepository.save(advertisement);

        return cancelResponse;
    }

    public void deleteAdvertisement() {
        Advertisement advertisement = advertisementRepository.findByTid(kakaoReady.getTid());
        advertisementRepository.delete(advertisement);
    }

    public void changeAdvertisement(MultipartFile image, ChangeAdRequestDto requestDto)
        throws IOException {
        Advertisement advertise = advertisementRepository.findById(requestDto.getAdvertiseId())
            .orElseThrow(() -> new RuntimeException("존재하지 않는 광고입니다."));
        advertise.setTitle(requestDto.getTitle());
        advertise.setText(requestDto.getText());
        advertise.setLocation(requestDto.getLocation());
        if (image != null) {
            advertise.setImage(s3UploadService.upload(image));
        }

        advertisementRepository.save(advertise);
    }

    public List<AdvertisementResponseDto> getCurrentAdvertisement(String location){
        LocalDate date = LocalDate.from(LocalDateTime.now().plusHours(9));
        List<Advertisement> currentAdvertisements = advertisementRepository.findCurrentAdvertisement(
            location, date.toString());
        List<AdvertisementResponseDto> responseDto = new ArrayList<>();
        for(Advertisement advertisement : currentAdvertisements){
            DesignerProfile designerProfile = designerProfileRepository.findById(advertisement.getAdvertiserId().getId())
                .orElseThrow(() -> new RuntimeException("디자이너 프로필 정보가 없습니다." ));
            responseDto.add(AdvertisementResponseDto.builder()
                .advertiseId(advertisement.getAdvertiseId())
                .advertiserId(advertisement.getAdvertiserId().getId())
                .advertiserImage(advertisement.getAdvertiserId().getProfileImage())
                .advertiserName(advertisement.getAdvertiserId().getName())
                .hairSalonAddress(designerProfile.getHairSalonAddress())
                .hairSalonName(designerProfile.getHairSalonName())
                .title(advertisement.getTitle())
                .text(advertisement.getText())
                .image(advertisement.getImage())
                .location(advertisement.getLocation()).build());
        }
        return responseDto;
    }

    public List<MyAdvertisementResponseDto> getMyAdvertisement(){
        List<Advertisement> advertisements = advertisementRepository.findByAdvertiserId(
            getCurrentUser());
        List<MyAdvertisementResponseDto> responseDto = new ArrayList<>();
        for(Advertisement advertisement: advertisements){
            responseDto.add(MyAdvertisementResponseDto.builder()
                .advertiseId(advertisement.getAdvertiseId())
                .title(advertisement.getTitle())
                .text(advertisement.getText())
                .image(advertisement.getImage())
                .startDate(advertisement.getStartDate().toString())
                .endDate(advertisement.getEndDate().toString())
                .state(advertisement.getState())
                .location(advertisement.getLocation())
                .build());
        }
        return responseDto;
    }

    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        String auth = "KakaoAK " + adminKey;
        httpHeaders.set("Authorization", auth);
        httpHeaders .add("Accept", "application/json");
        httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        return httpHeaders;
    }

    private User getCurrentUser() {
        User user = userRepository.findById(SecurityUtil.getCurrentMemberId())
            .orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다."));
        return user;
    }
}
