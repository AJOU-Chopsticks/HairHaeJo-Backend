package Chopsticks.HairHaeJoBackend.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReserveListDto {
    int reservationId;
    LocalDateTime Date;
    String menuName;
    String location;
    String userName;
    String tid;
    short state;
    long userId;
    int reviewId;
}
