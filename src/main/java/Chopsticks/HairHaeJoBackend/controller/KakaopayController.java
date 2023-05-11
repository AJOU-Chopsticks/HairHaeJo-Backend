package Chopsticks.HairHaeJoBackend.controller;

import Chopsticks.HairHaeJoBackend.dto.APIMessages;
import Chopsticks.HairHaeJoBackend.dto.Payment.KakaopayApproveResponse;
import Chopsticks.HairHaeJoBackend.dto.Payment.KakaopayCancelResponse;
import Chopsticks.HairHaeJoBackend.dto.Payment.KakaopayCancelrequest;
import Chopsticks.HairHaeJoBackend.dto.Payment.Kakaopayrequest;
import Chopsticks.HairHaeJoBackend.dto.article.ChangeArticleDto;
import Chopsticks.HairHaeJoBackend.service.KakaoPayService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class KakaopayController {

    private final KakaoPayService kakaoPayService;

    /**
     * 결제요청
     */
    @PostMapping("/ready")
    public ResponseEntity<APIMessages> readyToKakaoPay(@RequestParam("jsonList") String jsonList) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        Kakaopayrequest kakaopayDto = objectMapper.readValue(jsonList, new TypeReference<>() {});

        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("결제 준비 성공")
                .data(kakaoPayService.kakaoPayReady(kakaopayDto))
                .build();
        return ResponseEntity.ok(apiMessages);
    }

    /**
     * 결제 진행 중 취소
     */
    @GetMapping("/cancel")
    public ResponseEntity<APIMessages> cancel() {

        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("결제 진행중 취소하였습니다")
                .build();
        return ResponseEntity.ok(apiMessages);
    }

    /**
     * 결제 실패
     */
    @GetMapping("/fail")
    public ResponseEntity<APIMessages> fail() {

        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("결제 준비를 실패하였습니다")
                .build();
        return ResponseEntity.ok(apiMessages);
    }

    @GetMapping("/success")
    public ResponseEntity afterPayRequest(@RequestParam("pg_token") String pgToken) {

        KakaopayApproveResponse kakaoApprove = kakaoPayService. ApproveResponse(pgToken);

        return new ResponseEntity<>(kakaoApprove, HttpStatus.OK);
    }

    @PostMapping("/refund")
    public ResponseEntity refund(@RequestParam("jsonList") String jsonList) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        KakaopayCancelrequest kakaopayDto = objectMapper.readValue(jsonList, new TypeReference<>() {});

        KakaopayCancelResponse kakaoCancelResponse = kakaoPayService.kakaoCancel(kakaopayDto);

        return new ResponseEntity<>(kakaoCancelResponse, HttpStatus.OK);
    }

}