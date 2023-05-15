package Chopsticks.HairHaeJoBackend.service;


import Chopsticks.HairHaeJoBackend.dto.reservation.PossibleDayResponse;
import Chopsticks.HairHaeJoBackend.dto.reservation.ReserveListDto;
import Chopsticks.HairHaeJoBackend.entity.menu.DesignerMenuRepository;
import Chopsticks.HairHaeJoBackend.entity.reservation.ReservationRepository;
import Chopsticks.HairHaeJoBackend.entity.user.UserRepository;
import Chopsticks.HairHaeJoBackend.repository.DesignerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor

public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private DesignerMenuRepository designerMenuRepository;
    private DesignerProfileRepository designerProfileRepository;
    private UserRepository userRepository;

    public Collection<PossibleDayResponse> viewReservationDay(long designerId, LocalDateTime day1,LocalDateTime day2) {
        return reservationRepository.PossibleDay(designerId,day1,day2);
    }

    public Collection<ReserveListDto> viewReservationList(long clientId) {
        return reservationRepository.ViewListClient(clientId);
    }

    public Collection<ReserveListDto> viewReservationListDesigner(long clientId) {
        return reservationRepository.ViewListDesigner(clientId);
    }









}
