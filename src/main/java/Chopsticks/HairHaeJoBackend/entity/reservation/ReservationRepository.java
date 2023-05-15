package Chopsticks.HairHaeJoBackend.entity.reservation;

import Chopsticks.HairHaeJoBackend.dto.reservation.PossibleDayResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    Reservation findBytid(String tid);

    @Query(value = "SELECT distinct new Chopsticks.HairHaeJoBackend.dto.reservation.PossibleDayResponse(R.startTime)" +
            "FROM Reservation R WHERE R.designerId=:id And R.startTime>=:date1 And R.startTime<=:date2")
    Collection<PossibleDayResponse> PossibleDay(@Param(value="id") long designerId, LocalDateTime date1, LocalDateTime date2);



}

