package Chopsticks.HairHaeJoBackend.dto.designer;

import Chopsticks.HairHaeJoBackend.entity.designer.DesignerProfile;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
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
    private  Long userId;

    public DesignerProfile toDesignerProfile() {
        return DesignerProfile.builder()
            .userId(SecurityUtil.getCurrentMemberId())
                .introduction(introduction)
                .hairSalonName(hairSalonName)
                .hairSalonAddress(hairSalonAddress)
                .hairSalonNumber(hairSalonNumber)
                .build();
    }
}

