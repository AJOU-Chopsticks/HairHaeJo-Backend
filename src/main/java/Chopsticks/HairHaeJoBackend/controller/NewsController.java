package Chopsticks.HairHaeJoBackend.controller;

import Chopsticks.HairHaeJoBackend.dto.APIMessages;
import Chopsticks.HairHaeJoBackend.dto.news.NewsRequestDto;
import Chopsticks.HairHaeJoBackend.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/crm/news")
public class NewsController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<APIMessages> sendNews(@RequestBody NewsRequestDto requestDto) throws Exception {
        reservationService.sendNews(requestDto.getNews());
        APIMessages messages = APIMessages.builder()
            .success(true)
            .message("소식 전송 완료")
            .build();
        return ResponseEntity.ok(messages);
    }
}
