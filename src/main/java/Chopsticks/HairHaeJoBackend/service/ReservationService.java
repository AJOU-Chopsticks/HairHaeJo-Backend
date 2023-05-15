package Chopsticks.HairHaeJoBackend.service;


import Chopsticks.HairHaeJoBackend.dto.Payment.KakaopayApproveResponse;
import Chopsticks.HairHaeJoBackend.dto.reservation.PossibleDayResponse;
import Chopsticks.HairHaeJoBackend.dto.reservation.ReserveRequestDto;
import Chopsticks.HairHaeJoBackend.entity.menu.DesignerMenuRepository;
import Chopsticks.HairHaeJoBackend.entity.reservation.Reservation;
import Chopsticks.HairHaeJoBackend.entity.reservation.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

@Service
@RequiredArgsConstructor

public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private DesignerMenuRepository designerMenuRepository;

    public Collection<PossibleDayResponse> viewReservationDay(long designerId, LocalDateTime day1,LocalDateTime day2) {
        return reservationRepository.PossibleDay(designerId,day1,day2);
    }








}
