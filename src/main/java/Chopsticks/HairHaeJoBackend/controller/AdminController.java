package Chopsticks.HairHaeJoBackend.controller;

import Chopsticks.HairHaeJoBackend.dto.APIMessages;
import Chopsticks.HairHaeJoBackend.dto.Admin.AdvertisementApproveRequestDto;
import Chopsticks.HairHaeJoBackend.dto.Admin.LicenseApproveRequestDto;
import Chopsticks.HairHaeJoBackend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

	private final AdminService adminService;

	@GetMapping("/designer/signup")
	public ResponseEntity<APIMessages> getLicenseRequestList(){
		APIMessages messages = APIMessages.builder()
			.success(true)
			.message("디자이너 승인 요청 조회 완료")
			.data(adminService.getLicenseRequests())
			.build();
		return ResponseEntity.ok(messages);
	}

	@PostMapping("/designer/signup")
	public ResponseEntity<APIMessages> approveDesigner(@RequestBody LicenseApproveRequestDto requestDto)
		throws Exception {
		adminService.approveDesigner(requestDto);
		APIMessages messages = APIMessages.builder()
			.success(true)
			.message("디자이너 승인/거절 완료")
			.build();
		return ResponseEntity.ok(messages);
	}

	@GetMapping("/report")
	public ResponseEntity<APIMessages> getReports(){
		APIMessages messages = APIMessages.builder()
			.success(true)
			.message("신고 목록 조회 완료")
			.data(adminService.getReports())
			.build();
		return ResponseEntity.ok(messages);
	}

	@DeleteMapping("/report")
	public ResponseEntity<APIMessages> deleteReport(@RequestParam Long reportId){
		adminService.deleteReport(reportId);
		APIMessages messages = APIMessages.builder()
			.success(true)
			.message("신고 삭제 완료")
			.build();
		return ResponseEntity.ok(messages);
	}

	@DeleteMapping("/article")
	public ResponseEntity<APIMessages> deleteArticle(@RequestParam int articleId){
		adminService.deleteArticle(articleId);
		APIMessages messages = APIMessages.builder()
			.success(true)
			.message("요청글 삭제 완료")
			.build();
		return ResponseEntity.ok(messages);
	}

	@DeleteMapping("/review")
	public ResponseEntity<APIMessages> deleteReview(@RequestParam int reviewId){
		adminService.deleteReview(reviewId);
		APIMessages messages = APIMessages.builder()
			.success(true)
			.message("리뷰 삭제 완료")
			.build();
		return ResponseEntity.ok(messages);
	}

	@PutMapping("/user")
	public ResponseEntity<APIMessages> suspendUser(@RequestParam Long userId){
		adminService.suspendUser(userId);
		APIMessages messages = APIMessages.builder()
			.success(true)
			.message("유저 정지 완료")
			.build();
		return ResponseEntity.ok(messages);
	}

	@GetMapping("/ad")
	public ResponseEntity<APIMessages> getAdvertisements(){
		APIMessages messages = APIMessages.builder()
			.success(true)
			.message("광고 신청 목록 조회 완료")
			.data(adminService.getAdvertisements())
			.build();
		return ResponseEntity.ok(messages);
	}

	@PostMapping("/ad")
	public ResponseEntity<APIMessages> approveAdvertisements(@RequestBody AdvertisementApproveRequestDto requestDto)
		throws Exception {
		adminService.approveAdvertisement(requestDto);
		APIMessages messages = APIMessages.builder()
			.success(true)
			.message("광고 승인/거절 완료")
			.build();
		return ResponseEntity.ok(messages);
	}
}
