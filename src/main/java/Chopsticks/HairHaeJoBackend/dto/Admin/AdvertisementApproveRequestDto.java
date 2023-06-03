package Chopsticks.HairHaeJoBackend.dto.Admin;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AdvertisementApproveRequestDto {

    private Long advertiseId;
    private boolean approve;
}
