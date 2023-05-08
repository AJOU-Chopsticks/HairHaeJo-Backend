package Chopsticks.HairHaeJoBackend.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReserveRequestDto {
    String userId;
    String paymentId;
    String menuId;
    Date date;

}
