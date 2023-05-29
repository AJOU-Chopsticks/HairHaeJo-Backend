package Chopsticks.HairHaeJoBackend.controller;

import Chopsticks.HairHaeJoBackend.dto.APIMessages;

import Chopsticks.HairHaeJoBackend.dto.usermemo.UserMemoRequestDto;
import Chopsticks.HairHaeJoBackend.entity.user.UserRepository;
import Chopsticks.HairHaeJoBackend.repository.UserMemoRepository;
import Chopsticks.HairHaeJoBackend.service.UserMemoService;
import Chopsticks.HairHaeJoBackend.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/crm/customer/memo")
public class UserMemoController {

	private final UserMemoService userMemoService;


	//메모 등록
	@PostMapping("/{clientId}")
	public ResponseEntity<APIMessages> addusermemo(@RequestBody UserMemoRequestDto requestDto,
		@PathVariable long clientId) {
		userMemoService.addUserMemo(requestDto,clientId);
		APIMessages messages = APIMessages.builder()
			.success(true)
			.message("메모 생성완료")
			.build();
		return ResponseEntity.ok(messages);
	}
	//메모 조회
	@GetMapping("/{clientId}")
	public ResponseEntity<APIMessages> searchmemo(@PathVariable long clientId) {
		APIMessages messages = APIMessages.builder()
			.success(true)
			.message("메모 조회완료")
			.data(userMemoService.SearchUserMemo(clientId))
			.build();
		return ResponseEntity.ok(messages);
	}
	//메모 수정
	@PutMapping("/{clientId}")
	public ResponseEntity<APIMessages> changeusermemo(@RequestBody UserMemoRequestDto requestDto,
		@PathVariable long clientId) {
		userMemoService.changeUserMemo(requestDto,clientId);
		APIMessages messages = APIMessages.builder()
			.success(true)
			.message("메모 수정완료")
			.build();
		return ResponseEntity.ok(messages);
	}



}
