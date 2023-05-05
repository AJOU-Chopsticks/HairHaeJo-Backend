package Chopsticks.HairHaeJoBackend.controller;

import Chopsticks.HairHaeJoBackend.dto.APIMessages;
import Chopsticks.HairHaeJoBackend.dto.portfolio.ChangePortfolioRequestDto;
import Chopsticks.HairHaeJoBackend.dto.portfolio.PortfolioRequestDto;
import Chopsticks.HairHaeJoBackend.service.PortfolioService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/portfolio")
public class PortfolioController {

    private final PortfolioService portfolioService;

    //디자이너 포트폴리오 추가
    @PostMapping
    public ResponseEntity<APIMessages> save(
        @RequestPart(value = "image") MultipartFile image,
        @RequestParam("jsonList") String jsonList) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PortfolioRequestDto requestDto = objectMapper.readValue(jsonList, new TypeReference<>() {
        });
        portfolioService.save(image, requestDto);
        APIMessages messages = APIMessages.builder()
            .success(true)
            .message("포트폴리오 생성 완료")
            .build();
        return ResponseEntity.ok(messages);
    }

    //디자이너 포트폴리오 수정
    @PutMapping
    public ResponseEntity<APIMessages> change(
        @RequestPart(value = "image", required = false) MultipartFile image,
        @RequestParam("jsonList") String jsonList) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ChangePortfolioRequestDto requestDto = objectMapper.readValue(jsonList,
            new TypeReference<>() {
            });
        portfolioService.change(image, requestDto);
        APIMessages messages = APIMessages.builder()
            .success(true)
            .message("포트폴리오 수정 완료")
            .build();
        return ResponseEntity.ok(messages);
    }

    //(디자이너) 포트폴리오 리스트 조회
    @GetMapping
    public ResponseEntity<APIMessages> getPortfolios(@RequestParam Long designerId) {
        APIMessages messages = APIMessages.builder()
            .success(true)
            .message("포트폴리오 리스트 조회 완료")
            .data(portfolioService.getDesignerPortfolios(designerId))
            .build();
        return ResponseEntity.ok(messages);
    }

    //포트폴리오 삭제
    @DeleteMapping
    public ResponseEntity<APIMessages> delete(@RequestParam Long portfolioId) {
        portfolioService.delete(portfolioId);
        APIMessages messages = APIMessages.builder()
            .success(true)
            .message("포트폴리오 삭제 완료")
            .build();
        return ResponseEntity.ok(messages);
    }

    //포트폴리오 상세 조회
    @GetMapping("/view")
    public ResponseEntity<APIMessages> view(@RequestParam Long portfolioId) {
        APIMessages messages = APIMessages.builder()
            .success(true)
            .message("포트폴리오 상세조회 완료")
            .data(portfolioService.view(portfolioId))
            .build();
        return ResponseEntity.ok(messages);
    }

    //(지역,카테고리,태그,성별) 포트폴리오 리스트 조회
    @GetMapping("/style")
    public ResponseEntity<APIMessages> style(@RequestParam String region,
        @RequestParam String category, @RequestParam String tag, @RequestParam int gender) {
        APIMessages messages = APIMessages.builder()
            .success(true)
            .message("스타일 검색 완료")
            .data(portfolioService.getByStyle(region, category, tag, gender))
            .build();
        return ResponseEntity.ok(messages);
    }

}
