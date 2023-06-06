package Chopsticks.HairHaeJoBackend.entity.reservation;

import Chopsticks.HairHaeJoBackend.dto.reservation.ClientListInterface;
import Chopsticks.HairHaeJoBackend.dto.reservation.PossibleDayResponse;
import Chopsticks.HairHaeJoBackend.dto.reservation.ReserveListDto;
import Chopsticks.HairHaeJoBackend.dto.statistics.DataByAgeInterface;
import Chopsticks.HairHaeJoBackend.dto.statistics.DataByGenderInterface;
import Chopsticks.HairHaeJoBackend.dto.statistics.DataByMenuInterface;
import Chopsticks.HairHaeJoBackend.dto.statistics.DataByRevisitInterface;
import Chopsticks.HairHaeJoBackend.dto.statistics.MonthlyDataInterface;
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






    @Query(value = "select date_format(R.updated_at, '%Y-%m') as month, count(*) as count, sum(M.menu_price) as take "
        + "from Reservation R inner join "
        + "User U on R.client_id = U.user_id inner join "
        + "Designer_menu M on R.menu_id = M.menu_id "
        + "WHERE R.designer_id=:id "
        + "AND state = 2 "
        + "AND R.updated_at >= :year "
        + "AND R.updated_at < DATE_ADD(:year, INTERVAL 1 YEAR) "
        + "group by date_format(R.updated_at, '%Y-%m')", nativeQuery = true)
    List<MonthlyDataInterface> getMonthData(@Param(value = "id") String designerId, @Param(value = "year") String year);

    @Query(value = "select U.gender, count(*) as count, sum(M.menu_price) as take\n"
        + "from Reservation R inner join\n"
        + "\tUser U on R.client_id=U.user_id inner join\n"
        + "\tDesigner_menu M on R.menu_id=M.menu_id\n"
        + "WHERE R.designer_id=:id \n"
        + "\tAND state=2\n"
        + "\tAND R.updated_at >= :month \n"
        + "\tAND R.updated_at < DATE_ADD(:month, INTERVAL 1 MONTH)\n"
        + "group by U.gender", nativeQuery = true)
    List<DataByGenderInterface> getDataByGender(@Param(value = "id") String designerId, @Param(value = "month") String month);

    @Query(value = "select case\n"
        + "\twhen U.age < 10 then '0~9'\n"
        + "    when U.age < 20 then '10~19'\n"
        + "    when U.age < 30 then '20~29'\n"
        + "    when U.age < 40 then '30~39'\n"
        + "    when U.age < 50 then '40~49'\n"
        + "    when U.age < 60 then '50~59'\n"
        + "    else '60~'\n"
        + "    end as ageGroup,\n"
        + "    count(*) as count,\n"
        + "\tsum(M.menu_price) as take\n"
        + "from Reservation R inner join\n"
        + "\tUser U on R.client_id=U.user_id inner join\n"
        + "\tDesigner_menu M on R.menu_id=M.menu_id\n"
        + "WHERE R.designer_id=:id \n"
        + "\tAND state=2\n"
        + "\tAND R.updated_at >= :month \n"
        + "\tAND R.updated_at < DATE_ADD(:month, INTERVAL 1 MONTH)\n"
        + "group by ageGroup", nativeQuery = true)
    List<DataByAgeInterface> getDataByAge(@Param(value = "id") String designerId, @Param(value = "month") String month);

    @Query(value = "select M.menu_name as menu, count(*) as count, sum(M.menu_price) as take\n"
        + "from Reservation R inner join\n"
        + "\tUser U on R.client_id=U.user_id inner join\n"
        + "\tDesigner_menu M on R.menu_id=M.menu_id\n"
        + "WHERE R.designer_id=:id \n"
        + "\tAND state=2\n"
        + "\tAND R.updated_at >= :month \n"
        + "\tAND R.updated_at < DATE_ADD(:month, INTERVAL 1 MONTH)\n"
        + "group by M.menu_name", nativeQuery = true)
    List<DataByMenuInterface> getDataByMenu(@Param(value = "id") String designerId, @Param(value = "month") String month);

    @Query(value = "SELECT\n"
        + "    SUM(CASE WHEN R.client_id NOT IN (\n"
        + "            SELECT DISTINCT client_id\n"
        + "            FROM Reservation\n"
        + "            WHERE designer_id = :id\n"
        + "            AND updated_at < :month \n"
        + "        ) THEN 1 ELSE 0 END) AS newly,\n"
        + "    SUM(CASE WHEN R.client_id IN (\n"
        + "            SELECT DISTINCT client_id\n"
        + "            FROM Reservation\n"
        + "            WHERE designer_id = :id\n"
        + "            AND updated_at < :month \n"
        + "        ) THEN 1 ELSE 0 END) AS revisit,\n"
        + "    SUM(CASE WHEN R.client_id NOT IN (\n"
        + "            SELECT DISTINCT client_id\n"
        + "            FROM Reservation\n"
        + "            WHERE designer_id = :id\n"
        + "            AND updated_at < :month \n"
        + "        ) THEN M.menu_price ELSE 0 END) AS newlyTake,\n"
        + "    SUM(CASE WHEN R.client_id IN (\n"
        + "            SELECT DISTINCT client_id\n"
        + "            FROM Reservation\n"
        + "            WHERE designer_id = :id\n"
        + "            AND updated_at < :month \n"
        + "        ) THEN M.menu_price ELSE 0 END) AS revisitTake\n"
        + "FROM Reservation R\n"
        + "INNER JOIN User U ON R.client_id = U.user_id\n"
        + "INNER JOIN Designer_menu M ON R.menu_id = M.menu_id\n"
        + "WHERE R.designer_id = :id \n"
        + "    AND R.state = 2\n"
        + "    AND R.updated_at >= :month \n"
        + "    AND R.updated_at < DATE_ADD(:month, INTERVAL 1 MONTH)", nativeQuery = true)
    DataByRevisitInterface getDataByRevisit(@Param(value = "id") String designerId, @Param(value = "month") String month);

    @Query(value = "select client_id as clientId, count(client_id) as visitCount, ABS(timestampdiff(DAY, now(), max(updated_at))) as recentVisit\n"
        + "from Reservation\n"
        + "where designer_id=:id and state=2\n"
        + "group by client_id\n"
        + "order by recentVisit ASC;", nativeQuery = true)
    List<ClientListInterface> getClientList(@Param(value = "id") String designerId);

    @Query(value = "select distinct(client_id)\n"
        + "from Reservation\n"
        + "where state=2\n"
        + "and updated_at >=:date \n"
        + "and updated_at < DATE_ADD(:date, INTERVAL 1 DAY)", nativeQuery = true)
    List<Long> getRevisitAlarmList(@Param(value = "date")String date);
}


