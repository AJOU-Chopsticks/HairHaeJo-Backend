package Chopsticks.HairHaeJoBackend.dto.user;

import Chopsticks.HairHaeJoBackend.entity.user.ClientProfile;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientProfileRequestDto {
    private  int skinType;
    private  int hairType;
    private  int hairThickness;
    private  int dyeingHistory;
    private  int decolorizationHistory;
    private  String abstractLocation;
    private Long id;



    public ClientProfile toClientProfile(User user) {
        return ClientProfile.builder()
            .user(user)
        .skinType(skinType)
        .hairType(hairType)
        .hairThickness(hairThickness)
        .dyeingHistory(dyeingHistory)
        .decolorizationHistory(decolorizationHistory)
        .abstractLocation(abstractLocation)
        .build();


    }

}

