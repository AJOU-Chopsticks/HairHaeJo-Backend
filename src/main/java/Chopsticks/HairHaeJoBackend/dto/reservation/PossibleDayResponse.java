package Chopsticks.HairHaeJoBackend.dto.reservation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class PossibleDayResponse {
    LocalDateTime start;
    LocalDateTime end;


}
