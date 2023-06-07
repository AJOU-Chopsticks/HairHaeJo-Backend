package Chopsticks.HairHaeJoBackend.service;


import Chopsticks.HairHaeJoBackend.dto.holiday.HolidayDto;
import Chopsticks.HairHaeJoBackend.dto.reservation.ClientListInterface;
import Chopsticks.HairHaeJoBackend.dto.reservation.ClientListResponseDto;
import Chopsticks.HairHaeJoBackend.dto.reservation.DesignerReserveListDto;
import Chopsticks.HairHaeJoBackend.dto.reservation.PossibleDayResponse;
import Chopsticks.HairHaeJoBackend.dto.reservation.ImPossibleTimeResponse;
import Chopsticks.HairHaeJoBackend.dto.reservation.ReserveListDto;
import Chopsticks.HairHaeJoBackend.entity.designer.DesignerProfile;
import Chopsticks.HairHaeJoBackend.entity.holiday.DesignerHoliday;
import Chopsticks.HairHaeJoBackend.entity.holiday.DesignerHolidayRepository;
import Chopsticks.HairHaeJoBackend.entity.reservation.Reservation;
import Chopsticks.HairHaeJoBackend.entity.reservation.ReservationRepository;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import Chopsticks.HairHaeJoBackend.entity.user.UserRepository;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import Chopsticks.HairHaeJoBackend.entity.designer.DesignerProfileRepository;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor

public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final DesignerProfileRepository designerProfileRepository;
    private final UserRepository userRepository;
    private final DesignerHolidayRepository designerHolidayRepository;
    private final EmailService emailService;

    public ArrayList<ImPossibleTimeResponse> viewReservationDay(long designerId, LocalDateTime day1, LocalDateTime day2) {
        List<PossibleDayResponse> list=reservationRepository.PossibleDay(designerId,day1,day2);
        ListIterator<PossibleDayResponse> iterator = list.listIterator();

        ArrayList<ImPossibleTimeResponse> time=new ArrayList<>();
        day1=day1.plusHours(11);
        ZonedDateTime nowtime= ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

        LocalDateTime tempday=day1.plusHours(8);
        DesignerHoliday holiday =designerHolidayRepository.findBydesignerId(designerId);

        if(holiday==null) throw new RuntimeException();
        if(!holiday.getDesignerHoliday().equals("")&&isHoliday(day1.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).split("-"),day1.getDayOfWeek(),holiday.getDesignerHoliday().split(","))) {
            while(day1.isBefore(day2)) {
                String nowTime = day1.format(DateTimeFormatter.ofPattern("HH-mm"));
                time.add(new ImPossibleTimeResponse(nowTime));
                if(day1.isEqual(tempday)) break;
                day1 = day1.plusMinutes(30);
            }
            return time;
        }
        while(day1.isBefore(nowtime.toLocalDateTime())) {
            String nowTime=day1.format(DateTimeFormatter.ofPattern("HH-mm"));
            time.add(new ImPossibleTimeResponse(nowTime));
            if(day1.isEqual(tempday)) break;
            day1=day1.plusMinutes(30);
        }

        while(iterator.hasNext()) {
            PossibleDayResponse x = iterator.next();
            if(day1.isBefore(x.getStart())) day1=x.getStart();
            while (!day1.isAfter(x.getEnd())) {
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
        if(reservation.getDesignerId()!= SecurityUtil.getCurrentMemberId()) throw new RuntimeException("예약 대상자가 아닙니다");
        if(reservation.getState()==1) {
            if(LocalDateTime.now().isAfter(reservation.getStartTime())) reservation.setState((short)2);
            else throw new RuntimeException("아직 예약 시간이 되지 않았습니다");
        }
        else throw new RuntimeException("이미 완료 혹은 취소된 예약을 선택하셨습니다");
        reservationRepository.save(reservation);


    }

    public List<ClientListResponseDto> getClients(){
        List<ClientListInterface> clients = reservationRepository.getClientList(
                String.valueOf(getCurrentUser().getId()));
        List<ClientListResponseDto> responseDto = new ArrayList<>();

        for(ClientListInterface client : clients){
            User user = userRepository.findById(Long.valueOf(client.getClientId()))
                    .orElseThrow(() -> new RuntimeException("고객 정보가 없습니다."));
            responseDto.add(ClientListResponseDto.builder()
                    .clientId(user.getId())
                    .clientName(user.getName())
                    .profileImage(user.getProfileImage())
                    .recentVisit(client.getRecentVisit() + "일 전")
                    .visitCount(client.getVisitCount())
                    .build());
        }
        return responseDto;
    }

    public void sendNews(String news) throws Exception {
        User designer = getCurrentUser();
        List<ClientListInterface> clients = reservationRepository.getClientList(
            String.valueOf(designer.getId()));
        DesignerProfile profile = designerProfileRepository.findByUser(designer);
        for(ClientListInterface client : clients){
            User user = userRepository.findById(Long.valueOf(client.getClientId()))
                .orElseThrow(() -> new RuntimeException("고객 정보가 없습니다."));
            emailService.sendNews(user.getEmail(), designer.getProfileImage(), designer.getName() + " 디자이너",
                profile.getHairSalonName() + " (" + profile.getHairSalonNumber() + ")", news);
        }
    }

    private User getCurrentUser() {
        User user = userRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다."));
        return user;
    }

    private Boolean isHoliday(String[] nowday,DayOfWeek week,String[] holiday) {
        ArrayList<HolidayDto> ReturnData=new ArrayList<>();
        boolean isholiday=false;
        for (String s : holiday) {
            int holidayInt = Integer.parseInt(s);
            if (holidayInt > 100) {

                if (Integer.parseInt(nowday[2])==(holidayInt%100)) isholiday=true;


            } else {
                if(getWeekOfMONTH(nowday)==(holidayInt/10)&&(holidayInt%10)==week.getValue()) isholiday=true;


            }
        }
        return isholiday;

    }
    private int getWeekOfMONTH (String[] date) {
        Calendar calendar = Calendar.getInstance();
        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[2]);
        calendar.set(year, month - 1, day);
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }
}