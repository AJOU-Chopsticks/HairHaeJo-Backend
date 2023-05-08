package Chopsticks.HairHaeJoBackend.dto.user;


import Chopsticks.HairHaeJoBackend.entity.user.User;
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
public class ChangeClientProfileRequestDto {
    private Long id;
    private int skinType;
    private int hairType;
    private int hairThickness;
    private int dyeingHistory;
    private int decolorizationHistory;
    private String abstractLocation;

}

