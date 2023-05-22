package Chopsticks.HairHaeJoBackend.entity.reservation;

import Chopsticks.HairHaeJoBackend.dto.reservation.PossibleDayResponse;
import Chopsticks.HairHaeJoBackend.dto.reservation.ReserveListDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer>, ReservationRespositoryCustom {



    Reservation findBytid(String tid);

    @Query(value = "SELECT distinct new Chopsticks.HairHaeJoBackend.dto.reservation.PossibleDayResponse(R.startTime,R.endTime)" +
            "FROM Reservation R WHERE R.designerId=:id And R.startTime>=:date1 And R.startTime<=:date2 AND  (R.state=1 OR R.state=2 OR R.state=4) ORDER BY R.startTime ASC")
    List<PossibleDayResponse> PossibleDay(@Param(value="id") long designerId, LocalDateTime date1, LocalDateTime date2);








}


