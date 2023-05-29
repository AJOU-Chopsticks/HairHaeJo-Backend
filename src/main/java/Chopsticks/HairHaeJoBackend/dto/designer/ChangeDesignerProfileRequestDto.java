package Chopsticks.HairHaeJoBackend.dto.designer;


import Chopsticks.HairHaeJoBackend.entity.designer.DesignerProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import Chopsticks.HairHaeJoBackend.entity.user.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeDesignerProfileRequestDto {

    private Long id;
    private String introduction;
    private String hairSalonName;
    private String hairSalonAddress;
    private String hairSalonNumber;


}

