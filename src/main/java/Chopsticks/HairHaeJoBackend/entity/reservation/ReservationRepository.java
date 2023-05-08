package Chopsticks.HairHaeJoBackend.entity.reservation;

import Chopsticks.HairHaeJoBackend.dto.reservation.PossibleDayResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {



    @Query(value = "SELECT distinct new Chopsticks.HairHaeJoBackend.dto.reservation.PossibleDayResponse(R.startTime) from Reservation R JOIN R.user U where U.id=:designerId And Date(:nowdate) = Date(R.startTime)",nativeQuery = true)
    Collection<PossibleDayResponse> PossibleDay(@Param("designerId") long designerId,@Param("nowdate")String date);


}

