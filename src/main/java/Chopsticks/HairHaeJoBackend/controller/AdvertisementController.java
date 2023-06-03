package Chopsticks.HairHaeJoBackend.controller;

import Chopsticks.HairHaeJoBackend.dto.APIMessages;
import Chopsticks.HairHaeJoBackend.dto.Advertisement.AdvertisementRequestDto;
import Chopsticks.HairHaeJoBackend.dto.Advertisement.ChangeAdRequestDto;
import Chopsticks.HairHaeJoBackend.dto.Payment.KakaopayCancelResponse;
import Chopsticks.HairHaeJoBackend.service.AdvertisementService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ad")
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @PostMapping("/ready")
    public ResponseEntity<APIMessages> readyToKakaoPay(@RequestPart(value = "image") MultipartFile image, @RequestParam("jsonList") String jsonList) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        AdvertisementRequestDto kakaopayDto = objectMapper.registerModule(new JavaTimeModule()).readValue(jsonList, new TypeReference<>() {});

        APIMessages apiMessages=APIMessages.builder().success(true)
            .message("결제 준비 성공")
            .data(advertisementService.kakaoPayReady(image, kakaopayDto))
            .build();
        return ResponseEntity.ok(apiMessages);
    }

    @GetMapping("/cancel")
    public ResponseEntity<APIMessages> cancel() {
        advertisementService.deleteAdvertisement();
        APIMessages apiMessages=APIMessages.builder().success(true)
            .message("결제 진행중 취소하였습니다")
            .build();
        return ResponseEntity.ok(apiMessages);
    }

    @GetMapping("/fail")
    public ResponseEntity<APIMessages> fail() {
        advertisementService.deleteAdvertisement();
        APIMessages apiMessages=APIMessages.builder().success(true)
            .message("결제 준비를 실패하였습니다")
            .build();
        return ResponseEntity.ok(apiMessages);
    }

    @GetMapping("/success")
    public ResponseEntity<APIMessages> afterPayRequest(@RequestParam("pg_token") String pgToken,@RequestParam("tid") String tid) {
        advertisementService.approveResponse(pgToken,tid);
        APIMessages apiMessages=APIMessages.builder().success(true)
            .message("결제를 성공하였습니다")
            .build();
        return ResponseEntity.ok(apiMessages);
    }

    @PostMapping("/refund")
    public ResponseEntity<APIMessages> refund(@RequestParam Long advertiseId) {
        KakaopayCancelResponse kakaoCancelResponse = advertisementService.kakaoCancel(advertiseId);
        APIMessages apiMessages=APIMessages.builder().success(true)
            .message("결제를 취소하였습니다")
            .data(kakaoCancelResponse)
            .build();
        return ResponseEntity.ok(apiMessages);
    }

    @PutMapping("/change")
    public ResponseEntity<APIMessages> changeAdvertisement(@RequestPart(value = "image", required = false) MultipartFile image, @RequestParam("jsonList") String jsonList)
        throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        ChangeAdRequestDto requestDto = objectMapper.registerModule(new JavaTimeModule()).readValue(jsonList, new TypeReference<>() {});
        advertisementService.changeAdvertisement(image, requestDto);
        APIMessages apiMessages=APIMessages.builder().success(true)
            .message("광고 수정 완료")
            .build();
        return ResponseEntity.ok(apiMessages);
    }

    @GetMapping
    public ResponseEntity<APIMessages> getCurrentAdvertisement(@RequestParam String location){
        APIMessages apiMessages=APIMessages.builder().success(true)
            .message("현재 광고 조회 완료")
            .data(advertisementService.getCurrentAdvertisement(location))
            .build();
        return ResponseEntity.ok(apiMessages);
    }
}
