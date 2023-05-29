package Chopsticks.HairHaeJoBackend.controller;

import Chopsticks.HairHaeJoBackend.dto.APIMessages;
import Chopsticks.HairHaeJoBackend.dto.Payment.KakaopayApproveResponse;
import Chopsticks.HairHaeJoBackend.dto.Payment.KakaopayCancelResponse;
import Chopsticks.HairHaeJoBackend.dto.Payment.ReservationIdRequest;
import Chopsticks.HairHaeJoBackend.dto.Payment.Kakaopayrequest;
import Chopsticks.HairHaeJoBackend.service.FcmService;
import Chopsticks.HairHaeJoBackend.service.KakaoPayService;
import Chopsticks.HairHaeJoBackend.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/payment")
@CrossOrigin
@RequiredArgsConstructor
public class KakaopayController {

    private final KakaoPayService kakaoPayService;
    private final FcmService fcmService;
    private final UserService userService;

    /**
     * 결제요청
     */
    @PostMapping("/ready")
    public ResponseEntity<APIMessages> readyToKakaoPay(@RequestParam("jsonList") String jsonList) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        Kakaopayrequest kakaopayDto = objectMapper.registerModule(new JavaTimeModule()).readValue(jsonList, new TypeReference<>() {});

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
        kakaoPayService. deletereserve();

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
        kakaoPayService. deletereserve();
        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("결제 준비를 실패하였습니다")
                .build();
        return ResponseEntity.ok(apiMessages);
    }

    @GetMapping("/success")
    public ResponseEntity<APIMessages> afterPayRequest(@RequestParam("pg_token") String pgToken,@RequestParam("tid") String tid) throws Exception {

        KakaopayApproveResponse kakaopayApprove = kakaoPayService. ApproveResponse(pgToken,tid);
        String Fcmtoken=userService.getUserFcmToken(Long.parseLong(kakaopayApprove.getPartner_user_id()));
        fcmService.sendMessageTo(Fcmtoken,"새로운 예약이 등록되었습니다","지금 확인하세요");
        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("결제를 성공하였습니다")
                .build();

        return ResponseEntity.ok(apiMessages);
    }

    @PostMapping("/refund")
    public ResponseEntity<APIMessages> refund(@RequestParam("jsonList") String jsonList) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        ReservationIdRequest kakaopayDto = objectMapper.readValue(jsonList, new TypeReference<>() {});

        KakaopayCancelResponse kakaoCancelResponse = kakaoPayService.kakaoCancel(kakaopayDto);
        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("결제를 성공하였습니다")
                .data(kakaoCancelResponse)
                .build();

        return ResponseEntity.ok(apiMessages);
    }

}