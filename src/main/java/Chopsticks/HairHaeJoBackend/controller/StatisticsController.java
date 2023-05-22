package Chopsticks.HairHaeJoBackend.controller;

import Chopsticks.HairHaeJoBackend.dto.APIMessages;
import Chopsticks.HairHaeJoBackend.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/monthly")
    public ResponseEntity<APIMessages> getMonthlyData(@RequestParam int year){
        APIMessages messages = APIMessages.builder()
            .success(true)
            .message("월 별 시술 수, 매출 조회 성공")
            .data(statisticsService.getMonthlyData(year))
            .build();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/gender")
    public ResponseEntity<APIMessages> getDataByGender(@RequestParam int year, @RequestParam int month){
        APIMessages messages = APIMessages.builder()
            .success(true)
            .message("성별 별 시술 수, 매출 조회 성공")
            .data(statisticsService.getDataByGender(year, month))
            .build();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/age")
    public ResponseEntity<APIMessages> getDataByAge(@RequestParam int year, @RequestParam int month){
        APIMessages messages = APIMessages.builder()
            .success(true)
            .message("연령대 별 시술 수, 매출 조회 성공")
            .data(statisticsService.getDataByAge(year, month))
            .build();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/menu")
    public ResponseEntity<APIMessages> getDataByMenu(@RequestParam int year, @RequestParam int month){
        APIMessages messages = APIMessages.builder()
            .success(true)
            .message("시술 메뉴 별 시술 수, 매출 조회 성공")
            .data(statisticsService.getDataByMenu(year, month))
            .build();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/revisit")
    public ResponseEntity<APIMessages> getDataByRevisit(@RequestParam int year, @RequestParam int month){
        APIMessages messages = APIMessages.builder()
            .success(true)
            .message("신규/기존 고객 별 시술 수, 매출 조회 성공")
            .data(statisticsService.getDataByRevisit(year, month))
            .build();
        return ResponseEntity.ok(messages);
    }
}
