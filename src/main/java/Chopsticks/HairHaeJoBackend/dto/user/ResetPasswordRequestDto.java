package Chopsticks.HairHaeJoBackend.dto.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResetPasswordRequestDto {

    private String email;
    private String name;
}
