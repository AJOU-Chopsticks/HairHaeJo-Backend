package Chopsticks.HairHaeJoBackend.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

// SecurityContext 에 유저 정보가 저장되는 시점을 다루는 클래스
public class SecurityUtil {

    private SecurityUtil() {
    }

    // request가 들어오면 인증정보를 꺼내서 그 안의 id를 반환한다.
    public static Long getCurrentMemberId() {
        final Authentication authentication = SecurityContextHolder.getContext()
            .getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("Security Context에 인증 정보가 없습니다.");
        }

        return Long.parseLong(authentication.getName());
    }
}
