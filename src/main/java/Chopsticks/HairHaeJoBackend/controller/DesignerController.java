package Chopsticks.HairHaeJoBackend.controller;

import Chopsticks.HairHaeJoBackend.dto.APIMessages;
import Chopsticks.HairHaeJoBackend.dto.designer.ChangeDesignerProfileRequestDto;
import Chopsticks.HairHaeJoBackend.dto.designer.DesignerProfileRequestDto;
import Chopsticks.HairHaeJoBackend.service.DesignerLikeService;
import Chopsticks.HairHaeJoBackend.service.DesignerProfileService;

import Chopsticks.HairHaeJoBackend.service.DesignerRecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/designer")
public class DesignerController {

	private final DesignerProfileService designerProfileService;
	private final DesignerRecommendService designerRecommendService;
	private final DesignerLikeService designerLikeService;

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

	// 추천 디자이너
	@GetMapping("/recommend")
	public ResponseEntity<APIMessages> recommendDesigner(@RequestParam String region){
		APIMessages messages = APIMessages.builder()
			.success(true)
			.message("추천 디자이너 조회 완료")
			.data(designerRecommendService.getRecommendedDesigners(region))
			.build();
		return ResponseEntity.ok(messages);
	}

	// 관심 헤어디자이너 등록
	@PostMapping("/like")
	public ResponseEntity<APIMessages> addDesignerLike(@RequestParam Long designerId){
		designerLikeService.addDesignerLike(designerId);
		APIMessages messages = APIMessages.builder()
			.success(true)
			.message("관심 디자이너 등록 완료")
			.build();
		return ResponseEntity.ok(messages);
	}

	// 관심 헤어디자이너 취소
	@DeleteMapping("/like")
	public ResponseEntity<APIMessages> cancelDesignerLike(@RequestParam Long designerId){
		designerLikeService.cancelDesignerLike(designerId);
		APIMessages messages = APIMessages.builder()
			.success(true)
			.message("관심 디자이너 취소 완료")
			.build();
		return ResponseEntity.ok(messages);
	}

	// 관심 헤어디자이너 조회
	@GetMapping("/like")
	public ResponseEntity<APIMessages> getDesignerLike() {
		APIMessages messages = APIMessages.builder()
			.success(true)
			.message("관심 디자이너 조회 완료")
			.data(designerLikeService.getDesignerLike())
			.build();
		return ResponseEntity.ok(messages);
	}
}
