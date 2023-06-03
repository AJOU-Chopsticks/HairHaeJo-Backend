package Chopsticks.HairHaeJoBackend.dto.Admin;

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
public class AdvertisementRequestsResponseDto {

    private Long advertiseId;
    private String title;
    private String image;
    private String text;
    private String startDate;
    private String endDate;
    private String location;
}
