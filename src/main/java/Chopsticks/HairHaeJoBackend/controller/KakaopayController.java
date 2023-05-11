package Chopsticks.HairHaeJoBackend.controller;

import Chopsticks.HairHaeJoBackend.dto.APIMessages;
import Chopsticks.HairHaeJoBackend.dto.Payment.KakaopayApproveResponse;
import Chopsticks.HairHaeJoBackend.dto.Payment.KakaopayCancelResponse;
import Chopsticks.HairHaeJoBackend.service.KakaoPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class KakaopayController {

    private final KakaoPayService kakaoPayService;

    /**
     * 결제요청
     */
    @PostMapping("/ready")
    public ResponseEntity<APIMessages> readyToKakaoPay(@RequestParam("jsonList") String jsonList) {

        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("결제 준비 성공")
                .data(kakaoPayService.kakaoPayReady())
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
    public ResponseEntity refund() {

        KakaopayCancelResponse kakaoCancelResponse = kakaoPayService.kakaoCancel();

        return new ResponseEntity<>(kakaoCancelResponse, HttpStatus.OK);
    }

}