package Chopsticks.HairHaeJoBackend.controller;

import Chopsticks.HairHaeJoBackend.dto.APIMessages;
import Chopsticks.HairHaeJoBackend.dto.LoginRequestDto;
import Chopsticks.HairHaeJoBackend.dto.SignupRequestDto;
import Chopsticks.HairHaeJoBackend.service.UserService;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<APIMessages> signup(@RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
        APIMessages messages = APIMessages.builder()
            .success(true)
            .message("회원가입 완료")
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
}
