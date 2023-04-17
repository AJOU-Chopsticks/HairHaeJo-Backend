package Chopsticks.HairHaeJoBackend.dto;

import lombok.Builder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Builder
public class LoginRequestDto {

    private String email;
    private String password;

    // 아이디-비밀번호 일치 검증 로직
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
