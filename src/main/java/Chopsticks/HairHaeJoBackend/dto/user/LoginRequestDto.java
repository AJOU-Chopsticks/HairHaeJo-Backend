package Chopsticks.HairHaeJoBackend.dto.user;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Builder
@Getter
public class LoginRequestDto {

    private String email;
    private String password;
    private String fcmToken;

    // 아이디-비밀번호 일치 검증 로직
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
