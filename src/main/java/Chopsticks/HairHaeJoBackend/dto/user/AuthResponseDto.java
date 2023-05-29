package Chopsticks.HairHaeJoBackend.dto.user;

import Chopsticks.HairHaeJoBackend.entity.user.Role;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDto {

    private Long userId;
    private String email;
    private String name;
    private Role role;
    private String phoneNumber;
    private String profileImage;
    private int gender;
    private int age;
    private String location;

    public static AuthResponseDto of(User user) {
        return AuthResponseDto.builder()
            .userId(user.getId())
            .email(user.getEmail())
            .name(user.getName())
            .role(user.getRole())
            .phoneNumber(user.getPhoneNumber())
            .profileImage(user.getProfileImage())
            .gender(user.getGender())
            .age(user.getAge())
            .build();
    }
}
