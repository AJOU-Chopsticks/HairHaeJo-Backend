package Chopsticks.HairHaeJoBackend.controller;

import Chopsticks.HairHaeJoBackend.dto.APIMessages;
import Chopsticks.HairHaeJoBackend.dto.designer.DesignerProfileRequestDto;
import Chopsticks.HairHaeJoBackend.service.DesignerProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
