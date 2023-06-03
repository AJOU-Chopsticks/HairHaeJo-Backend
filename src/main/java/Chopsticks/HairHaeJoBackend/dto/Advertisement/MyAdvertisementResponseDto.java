package Chopsticks.HairHaeJoBackend.dto.Advertisement;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MyAdvertisementResponseDto {

    private Long advertiseId;
    private String title;
    private String image;
    private String text;
    private String location;
    private String startDate;
    private String endDate;
    private int state;
}
