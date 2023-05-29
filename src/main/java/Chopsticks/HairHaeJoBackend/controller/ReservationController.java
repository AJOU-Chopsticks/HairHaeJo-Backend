package Chopsticks.HairHaeJoBackend.controller;


import Chopsticks.HairHaeJoBackend.dto.APIMessages;
import Chopsticks.HairHaeJoBackend.dto.Payment.ReservationIdRequest;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import Chopsticks.HairHaeJoBackend.service.ReservationService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping("")
    public ResponseEntity<APIMessages> viewReservation(@RequestParam(value = "designerId") String designerId, @RequestParam(value = "reservationday") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)  {

        LocalDateTime localDateTime1 = date.atStartOfDay();
        LocalDateTime localDateTime2 = date.plusDays(1).atStartOfDay();
        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("예약 가능 시간 데이터")
                .data(reservationService.viewReservationDay(Long.parseLong(designerId),localDateTime1,localDateTime2))
                .build();
        return ResponseEntity.ok(apiMessages);
    }


    @GetMapping("/list/client")
    public ResponseEntity<APIMessages> clienteReserveList() throws IOException

    {
        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("예약 리스트 확인(고객)")
                .data(reservationService.viewReservationList(SecurityUtil.getCurrentMemberId()))
                .build();
        return ResponseEntity.ok(apiMessages);
    }

    @GetMapping("/list/designer/progress")
    public ResponseEntity<APIMessages> designerreserveList() throws IOException

    {
        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("예약 리스트 확인(디자이너)")
                .data(reservationService.viewReservationListDesigner(SecurityUtil.getCurrentMemberId()))
                .build();
        return ResponseEntity.ok(apiMessages);
    }

    @GetMapping("/list/designer/finished")
    public ResponseEntity<APIMessages> designerfinishList() throws IOException

    {
        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("예약 리스트 확인(디자이너)")
                .data(reservationService.viewFinishedListDesigner(SecurityUtil.getCurrentMemberId()))
                .build();
        return ResponseEntity.ok(apiMessages);
    }
    @PutMapping("/finish")
    public ResponseEntity<APIMessages> reserveFinish(@RequestParam("jsonList") String jsonList) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        ReservationIdRequest reservationDto = objectMapper.readValue(jsonList, new TypeReference<>() {});
        reservationService.finishReservation(reservationDto.getReservation_id());
        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("시술 완료 반영 완료")
                .build();
        return ResponseEntity.ok(apiMessages);

    }






}
