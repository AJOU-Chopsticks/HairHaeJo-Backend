package Chopsticks.HairHaeJoBackend.controller;

import Chopsticks.HairHaeJoBackend.dto.APIMessages;
import Chopsticks.HairHaeJoBackend.dto.designer.DesignerProfileRequestDto;
import Chopsticks.HairHaeJoBackend.dto.designer.PortfolioRequestDto;
import Chopsticks.HairHaeJoBackend.service.DesignerProfileService;
import Chopsticks.HairHaeJoBackend.service.PortfolioService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/designer")
public class DesignerController {

    private final DesignerProfileService designerProfileService;
    private final PortfolioService portfolioService;

    //디자이너 프로필 설정
    @PostMapping("/profile")
    public ResponseEntity<APIMessages> defaultdesignerprofile(
        @RequestBody DesignerProfileRequestDto requestDto) {
        designerProfileService.defaultdesignerprofile(requestDto);
        APIMessages messages = APIMessages.builder()
                .success(true)
                .message("디자이너프로필 생성완료")
                .build();
        return ResponseEntity.ok(messages);
    }

    //디자이너 포트폴리오 추가
    @PostMapping("/portfolio")
    public ResponseEntity<APIMessages> portfolioadd(@RequestPart(value = "image", required = false) MultipartFile image,
                                              @RequestParam("jsonList") String jsonList) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        PortfolioRequestDto requestDto = objectMapper.readValue(jsonList, new TypeReference<>() {
        });
        portfolioService.portfolioadd(image, requestDto);
        APIMessages messages = APIMessages.builder()
                .success(true)
                .message("디자이너 포트폴리오 생성 완료")
                .build();
        return ResponseEntity.ok(messages);
    }
    


}
