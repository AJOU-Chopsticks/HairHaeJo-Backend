package Chopsticks.HairHaeJoBackend.dto.designer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RecommendResponseDto {

    private Long designerId;
    private String Name;
    private String ProfileImage;
}
