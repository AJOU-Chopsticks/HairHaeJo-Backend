package Chopsticks.HairHaeJoBackend.dto.designer;

import Chopsticks.HairHaeJoBackend.dto.user.ChangeClientProfileRequestDto;
import Chopsticks.HairHaeJoBackend.entity.designer.DesignerProfile;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DesignerProfileSearchResponseDto {

    private String introduction;
    private String hairSalonName;
    private String hairSalonAddress;
    private String hairSalonNumber;

    public static DesignerProfileSearchResponseDto of(DesignerProfile designerProfile){
        return DesignerProfileSearchResponseDto.builder()
                .introduction(designerProfile.getIntroduction())
                .hairSalonName(designerProfile.getHairSalonName())
                .hairSalonAddress(designerProfile.getHairSalonAddress())
                .hairSalonNumber(designerProfile.getHairSalonNumber())
                .build();
    }

}

