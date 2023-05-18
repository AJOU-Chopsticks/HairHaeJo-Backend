package Chopsticks.HairHaeJoBackend.entity.reservation;

import Chopsticks.HairHaeJoBackend.dto.reservation.PossibleDayResponse;
import Chopsticks.HairHaeJoBackend.dto.reservation.ReserveListDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

}