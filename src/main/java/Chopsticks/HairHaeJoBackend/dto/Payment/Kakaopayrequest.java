package Chopsticks.HairHaeJoBackend.dto.Payment;

import Chopsticks.HairHaeJoBackend.entity.article.Article;
import Chopsticks.HairHaeJoBackend.entity.article.Articlestate;

import Chopsticks.HairHaeJoBackend.entity.reservation.Reservation;
import Chopsticks.HairHaeJoBackend.entity.reservation.ReservationRepository;
import Chopsticks.HairHaeJoBackend.entity.reservation.ReservationState;
import Chopsticks.HairHaeJoBackend.service.ReservationService;
import com.google.api.client.util.DateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Kakaopayrequest {

    private long designer_id;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime start_time;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime end_time;
    private int menu_id;


    public Reservation toReservation (long user, String tid, short state) {
        return Reservation.builder()
                .clientId(user)
                .designerId(designer_id)
                .menuId(menu_id)
                .startTime(start_time)
                .endTime(end_time)
                .tid(tid)
                .state(state)
                .build();
    }

}
