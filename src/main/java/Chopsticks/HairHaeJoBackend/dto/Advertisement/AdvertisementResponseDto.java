package Chopsticks.HairHaeJoBackend.dto.Advertisement;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AdvertisementResponseDto {

    private Long advertiserId;
    private String advertiserName;
    private String advertiserImage;
    private String hairSalonName;
    private String hairSalonAddress;
    private Long advertiseId;
    private String title;
    private String image;
    private String text;
    private String location;
}
