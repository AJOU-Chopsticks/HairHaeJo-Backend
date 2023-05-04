package Chopsticks.HairHaeJoBackend.controller;

import Chopsticks.HairHaeJoBackend.dto.APIMessages;
import Chopsticks.HairHaeJoBackend.dto.report.ReportRequestDto;
import Chopsticks.HairHaeJoBackend.dto.user.ChangePasswordRequestDto;
import Chopsticks.HairHaeJoBackend.dto.user.ClientProfileRequestDto;
import Chopsticks.HairHaeJoBackend.dto.user.LoginRequestDto;
import Chopsticks.HairHaeJoBackend.dto.user.ResetPasswordRequestDto;
import Chopsticks.HairHaeJoBackend.dto.user.SignupRequestDto;
import Chopsticks.HairHaeJoBackend.service.EmailService;
import Chopsticks.HairHaeJoBackend.service.UserService;
import Chopsticks.HairHaeJoBackend.service.ClientProfileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
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
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private final ClientProfileService clientProfileService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<APIMessages> signup(
        @RequestPart(value = "profileImage", required = false) MultipartFile image,
        @RequestParam("jsonList") String jsonList) throws IOException {
        APIMessages messages = APIMessages.builder()
            .success(true)
            .message("회원가입 완료")
            .data(userService.signup(image, jsonToSignupRequestDto(jsonList)))
            .build();
        return ResponseEntity.ok(messages);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<APIMessages> login(@RequestBody LoginRequestDto requestDto,
        HttpServletResponse response) {
        APIMessages messages = APIMessages.builder()
            .success(true)
            .message("로그인 성공")
            .data(userService.login(requestDto, response))
            .build();
        return ResponseEntity.ok(messages);
    }

    //인증
    @GetMapping("/auth")
    public ResponseEntity<APIMessages> auth() {
        APIMessages messages = APIMessages.builder()
            .success(true)
            .message("인증 성공")
            .data(userService.auth())
            .build();
        return ResponseEntity.ok(messages);
    }

    //계정 정보 변경
    @PutMapping("/account")
    public ResponseEntity<APIMessages> changeAccountInfo(
        @RequestPart(value = "profileImage", required = false) MultipartFile image,
        @RequestParam("jsonList") String jsonList) throws IOException {
        userService.changeAccountInfo(image, jsonToSignupRequestDto(jsonList));
        APIMessages messages = APIMessages.builder()
            .success(true)
            .message("계정 정보 변경 완료")
            .build();
        return ResponseEntity.ok(messages);
    }

    //비밀번호 변경
    @PutMapping("/password")
    public ResponseEntity<APIMessages> changePassword(
        @RequestBody ChangePasswordRequestDto requestDto) {
        userService.changePassword(requestDto);
        APIMessages messages = APIMessages.builder()
            .success(true)
            .message("비밀번호 변경 완료")
            .build();
        return ResponseEntity.ok(messages);
    }

    //헤어 디자이너 인증
    @PostMapping("/license")
    public ResponseEntity<APIMessages> licenseRegister(
        @RequestPart(value = "licenseImage") MultipartFile image)
        throws IOException {
        userService.licenseRegister(image);
        APIMessages messages = APIMessages.builder()
            .success(true)
            .message("신청 완료")
            .build();
        return ResponseEntity.ok(messages);
    }

    //회원탈퇴
    @DeleteMapping()
    public ResponseEntity<APIMessages> withdrawal(@RequestBody LoginRequestDto requestDto) {
        userService.withdrawal(requestDto);
        APIMessages messages = APIMessages.builder()
            .success(true)
            .message("회원탈퇴 완료")
            .build();
        return ResponseEntity.ok(messages);
    }

    // 이메일 인증
    @PostMapping("/email")
    public ResponseEntity<APIMessages> email(@RequestParam String email) throws Exception {
        APIMessages messages = APIMessages.builder()
            .success(true)
            .message("인증코드 생성 및 이메일 발송 완료")
            .data(emailService.sendConfirmMessage(email))
            .build();
        return ResponseEntity.ok(messages);
    }

    // 비밀번호 찾기
    @PostMapping("/resetPassword")
    public ResponseEntity<APIMessages> resetPassword(@RequestBody ResetPasswordRequestDto requestDto)
        throws Exception {
        emailService.resetPassword(requestDto);
        APIMessages messages = APIMessages.builder()
            .success(true)
            .message("비밀번호 초기화 및 이메일 발송 완료")
            .build();
        return ResponseEntity.ok(messages);
    }

    //사용자 신고
    @PostMapping("/report")
    public ResponseEntity<APIMessages> report(@RequestBody ReportRequestDto requestDto){
        userService.report(requestDto);
        APIMessages messages = APIMessages.builder()
            .success(true)
            .message("사용자 신고 완료")
            .build();
        return ResponseEntity.ok(messages);
    }

    public SignupRequestDto jsonToSignupRequestDto(String jsonList) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        SignupRequestDto requestDto = objectMapper.readValue(jsonList, new TypeReference<>() {
        });
        return requestDto;
    }

    @PostMapping("/profile")
    public ResponseEntity<APIMessages> defaultClientProfile(@RequestBody ClientProfileRequestDto requestDto)
    {
        clientProfileService.defaultClientProfile(requestDto);
        APIMessages messages = APIMessages.builder()
            .success(true)
            .message("초기 고객 프로필 생성완료")
            .build();
        return ResponseEntity.ok(messages);
    }





}


