package Chopsticks.HairHaeJoBackend.controller;

import Chopsticks.HairHaeJoBackend.dto.APIMessages;
import Chopsticks.HairHaeJoBackend.dto.designer.ChangeDesignerProfileRequestDto;
import Chopsticks.HairHaeJoBackend.dto.designer.DesignerProfileRequestDto;
import Chopsticks.HairHaeJoBackend.service.DesignerProfileService;
import Chopsticks.HairHaeJoBackend.entity.user.User;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	//디자이너 프로필 조회
	@GetMapping("/profile/{designerId}")
	public ResponseEntity<APIMessages> DesignerProfileSearchResponseDto(@PathVariable Long designerId) {
		APIMessages messages = APIMessages.builder()
			.success(true)
			.message("조회 성공")
			.data(designerProfileService.SearchDesignerProfile(designerId))
			.build();

		return ResponseEntity.ok(messages);
	}


	//디자이너 프로필 수정
	@PutMapping("/profile")
	public ResponseEntity<APIMessages> changeDesignerProfile(@RequestBody ChangeDesignerProfileRequestDto requestDto){
		designerProfileService.changeDesignerProfile(requestDto);
		APIMessages messages = APIMessages.builder()
			.success(true)
			.message("고객 프로필 변경 성공")
			.build();
		return ResponseEntity.ok(messages);
	}


}
