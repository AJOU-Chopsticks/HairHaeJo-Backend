package Chopsticks.HairHaeJoBackend.controller;

import Chopsticks.HairHaeJoBackend.dto.APIMessages;
import Chopsticks.HairHaeJoBackend.dto.designer.ChangePortfolioRequestDto;
import Chopsticks.HairHaeJoBackend.dto.designer.DesignerProfileRequestDto;
import Chopsticks.HairHaeJoBackend.dto.designer.PortfolioRequestDto;
import Chopsticks.HairHaeJoBackend.service.DesignerProfileService;
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
}
