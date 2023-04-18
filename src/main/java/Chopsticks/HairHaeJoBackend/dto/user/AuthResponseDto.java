package Chopsticks.HairHaeJoBackend.dto.user;

import Chopsticks.HairHaeJoBackend.entity.Role;
import Chopsticks.HairHaeJoBackend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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