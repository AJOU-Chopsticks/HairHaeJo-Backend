package Chopsticks.HairHaeJoBackend.dto.designer;

import Chopsticks.HairHaeJoBackend.entity.designer.DesignerProfile;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DesignerProfileRequestDto {

    private String introduction;
    private String hairSalonName;
    private String hairSalonAddress;
    private String hairSalonNumber;
    private Long id;

    public DesignerProfile toDesignerProfile(User user) {
        return DesignerProfile.builder()
                .user(user)
                .introduction(introduction)
                .hairSalonName(hairSalonName)
                .hairSalonAddress(hairSalonAddress)
                .hairSalonNumber(hairSalonNumber)
                .build();
    }
}

