package Chopsticks.HairHaeJoBackend.service;


import Chopsticks.HairHaeJoBackend.dto.reservation.DesignerReserveListDto;
import Chopsticks.HairHaeJoBackend.dto.reservation.PossibleDayResponse;
import Chopsticks.HairHaeJoBackend.dto.reservation.ImPossibleTimeResponse;
import Chopsticks.HairHaeJoBackend.dto.reservation.ReserveListDto;
import Chopsticks.HairHaeJoBackend.entity.menu.DesignerMenuRepository;
import Chopsticks.HairHaeJoBackend.entity.reservation.Reservation;
import Chopsticks.HairHaeJoBackend.entity.reservation.ReservationRepository;
import Chopsticks.HairHaeJoBackend.entity.user.UserRepository;
import Chopsticks.HairHaeJoBackend.repository.DesignerProfileRepository;
import com.google.gson.JsonObject;
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

    public ArrayList<ImPossibleTimeResponse> viewReservationDay(long designerId, LocalDateTime day1, LocalDateTime day2) {
        List<PossibleDayResponse> list=reservationRepository.PossibleDay(designerId,day1,day2);
        ListIterator<PossibleDayResponse> iterator = list.listIterator();
        //정방향 출력
        ArrayList<ImPossibleTimeResponse> time=new ArrayList<>();
        day1=day1.plusHours(11);
        while(iterator.hasNext()) {
            PossibleDayResponse x = iterator.next();
            if(day1.isBefore(x.getStart())) day1=x.getStart();
            while (!day1.isEqual(x.getEnd())) {
                    JsonObject temp = new JsonObject();
                    String nowTime=day1.format(DateTimeFormatter.ofPattern("HH-mm"));
                    time.add(new ImPossibleTimeResponse(nowTime));
                    day1=day1.plusMinutes(30);
                }
        }

        return time;
    }

    public Collection<ReserveListDto> viewReservationList(long clientId) {
        
        return reservationRepository.ViewListClient(clientId);
    }

    public Collection<DesignerReserveListDto> viewReservationListDesigner(long designerId) {
        return reservationRepository.getDesignerReserveList(designerId,(short)1);
    }

    public Collection<DesignerReserveListDto> viewFinishedListDesigner(long designerId) {
        return reservationRepository.getDesignerReserveList(designerId,(short)2);
    }

    public void finishReservation(int reservationId) {
        Reservation reservation;
        if(reservationRepository.findById(reservationId).isPresent()) {
            reservation=reservationRepository.findById(reservationId).get();
        }
        else throw new RuntimeException("존재하지 않는 예약입니다");
        if(reservation.getState()==1) {
            if(LocalDateTime.now().isAfter(reservation.getStartTime())) reservation.setState((short)2);
            else throw new RuntimeException("아직 예약 시간이 되지 않았습니다");
        }
        else throw new RuntimeException("이미 완료 혹은 취소된 예약을 선택하셨습니다");


    }









}
