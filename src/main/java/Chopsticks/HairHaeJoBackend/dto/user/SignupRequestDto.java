package Chopsticks.HairHaeJoBackend.dto.user;

import Chopsticks.HairHaeJoBackend.entity.user.Role;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupRequestDto {

    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private int gender;
    private int age;

    public User toUser(String image, PasswordEncoder passwordEncoder) {
        return User.builder()
            .email(email)
            .password(passwordEncoder.encode(password))
            .name(name)
            .phoneNumber(phoneNumber)
            .gender(gender)
            .age(age)
            .role(Role.ROLE_USER)
            .profileImage(image)
            .build();
    }
}
