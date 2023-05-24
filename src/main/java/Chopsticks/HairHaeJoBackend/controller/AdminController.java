package Chopsticks.HairHaeJoBackend.controller;

import Chopsticks.HairHaeJoBackend.dto.APIMessages;
import Chopsticks.HairHaeJoBackend.dto.Admin.LicenseApproveRequestDto;
import Chopsticks.HairHaeJoBackend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	public ResponseEntity<APIMessages> approveDesigner(@RequestBody LicenseApproveRequestDto requestDto){
		adminService.approveDesigner(requestDto);
		APIMessages messages = APIMessages.builder()
			.success(true)
			.message("디자이너 승인/거절 완료")
			.build();
		return ResponseEntity.ok(messages);
	}
}
