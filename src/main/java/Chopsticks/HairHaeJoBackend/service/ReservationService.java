package Chopsticks.HairHaeJoBackend.service;


import Chopsticks.HairHaeJoBackend.dto.reservation.PossibleDayResponse;
import Chopsticks.HairHaeJoBackend.dto.reservation.PossibleTimeResponse;
import Chopsticks.HairHaeJoBackend.dto.reservation.ReserveListDto;
import Chopsticks.HairHaeJoBackend.entity.menu.DesignerMenuRepository;
import Chopsticks.HairHaeJoBackend.entity.reservation.ReservationRepository;
import Chopsticks.HairHaeJoBackend.entity.user.UserRepository;
import Chopsticks.HairHaeJoBackend.repository.DesignerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor

public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private DesignerMenuRepository designerMenuRepository;
    private DesignerProfileRepository designerProfileRepository;
    private UserRepository userRepository;

    public ArrayList<String> viewReservationDay(long designerId, LocalDateTime day1, LocalDateTime day2) {
        List<PossibleDayResponse> list=reservationRepository.PossibleDay(designerId,day1,day2);
        ListIterator<PossibleDayResponse> iterator = list.listIterator();
        //정방향 출력
        ArrayList<String> time=new ArrayList<>();

        while(iterator.hasNext()){
            PossibleDayResponse x=iterator.next();
            String nowTime=x.getStart().format(DateTimeFormatter.ofPattern("HH-mm"));
            System.out.println(nowTime);
            PossibleTimeResponse now=new PossibleTimeResponse(nowTime);
            time.add(nowTime);

        }
        return time;
    }

    public Collection<ReserveListDto> viewReservationList(long clientId) {
        return reservationRepository.ViewListClient(clientId);
    }

    public Collection<ReserveListDto> viewReservationListDesigner(long clientId) {
        return reservationRepository.ViewListDesigner(clientId);
    }









}
