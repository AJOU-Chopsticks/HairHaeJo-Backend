package Chopsticks.HairHaeJoBackend.service;


import Chopsticks.HairHaeJoBackend.dto.reservation.PossibleDayResponse;
import Chopsticks.HairHaeJoBackend.dto.reservation.ReserveRequestDto;
import Chopsticks.HairHaeJoBackend.entity.menu.DesignerMenuRepository;
import Chopsticks.HairHaeJoBackend.entity.reservation.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ReservationService {

    private ReservationRepository reservationRepository;
    private DesignerMenuRepository designerMenuRepository;
    public Collection<PossibleDayResponse> viewReservationDay(long designerId,String day) {
        return reservationRepository.PossibleDay(designerId,day);
    }
    /*
    public Collection<PossibleDayResponse> reserve(ReserveRequestDto reserveDto) {

        reserveDto.getUserId();
    }

     */


}
