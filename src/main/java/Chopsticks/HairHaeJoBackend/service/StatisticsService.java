package Chopsticks.HairHaeJoBackend.service;

import Chopsticks.HairHaeJoBackend.dto.statistics.DataByAgeInterface;
import Chopsticks.HairHaeJoBackend.dto.statistics.DataByGenderInterface;
import Chopsticks.HairHaeJoBackend.dto.statistics.DataByMenuInterface;
import Chopsticks.HairHaeJoBackend.dto.statistics.DataByRevisitInterface;
import Chopsticks.HairHaeJoBackend.dto.statistics.MonthlyDataInterface;
import Chopsticks.HairHaeJoBackend.entity.reservation.ReservationRepository;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import Chopsticks.HairHaeJoBackend.entity.user.UserRepository;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StatisticsService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    public List<MonthlyDataInterface> getMonthlyData(int year){
        User user = getCurrentUser();
        return reservationRepository.getMonthData(String.valueOf(user.getId()), year + "-01-01");
    }

    public List<DataByGenderInterface> getDataByGender(int year, int month){
        User user = getCurrentUser();
        return reservationRepository.getDataByGender(String.valueOf(user.getId()), year + "-" + month + "-01");
    }

    public List<DataByAgeInterface> getDataByAge(int year, int month){
        User user = getCurrentUser();
        return reservationRepository.getDataByAge(String.valueOf(user.getId()), year + "-" + month + "-01");
    }

    public List<DataByMenuInterface> getDataByMenu(int year, int month){
        User user = getCurrentUser();
        return reservationRepository.getDataByMenu(String.valueOf(user.getId()), year + "-" + month + "-01");
    }

    public DataByRevisitInterface getDataByRevisit(int year, int month){
        User user = getCurrentUser();
        return reservationRepository.getDataByRevisit(String.valueOf(user.getId()), year + "-" + month + "-01");
    }

    private User getCurrentUser() {
        User user = userRepository.findById(SecurityUtil.getCurrentMemberId())
            .orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다."));
        return user;
    }
}
