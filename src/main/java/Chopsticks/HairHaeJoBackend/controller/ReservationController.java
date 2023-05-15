package Chopsticks.HairHaeJoBackend.controller;


import Chopsticks.HairHaeJoBackend.dto.APIMessages;
import Chopsticks.HairHaeJoBackend.dto.reservation.ReserveRequestDto;
import Chopsticks.HairHaeJoBackend.service.ReservationService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping("")
    public ResponseEntity<APIMessages> viewReservation(@RequestParam(value = "designerId") String designerId, @RequestParam(value = "reservationday") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) throws IOException, ParseException {

        LocalDateTime localDateTime1 = date.atStartOfDay();
        LocalDateTime localDateTime2 = date.plusDays(1).atStartOfDay();
        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("예약 가능 시간 데이터")
                .data(reservationService.viewReservationDay(Long.parseLong(designerId),localDateTime1,localDateTime2))
                .build();
        return ResponseEntity.ok(apiMessages);
    }

     /*
    @PostMapping("/request")
    public ResponseEntity<APIMessages> reserve(@RequestParam("jsonList") String jsonList) throws IOException

    {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        ReserveRequestDto ReserveDto = objectMapper.readValue(jsonList, new TypeReference<>() {});

        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("예약 가능 시간 데이터")
                .data(reservationService.reserve(ReserveDto))
                .build();
        return ResponseEntity.ok(apiMessages);
    }

      */



}
