package Chopsticks.HairHaeJoBackend.entity.reservation;

import Chopsticks.HairHaeJoBackend.dto.reservation.DesignerReserveListDto;
import Chopsticks.HairHaeJoBackend.dto.reservation.ReserveListDto;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ReservationRespositoryCustom {
    Collection<DesignerReserveListDto> getDesignerReserveList(long desinger, short state);
    Collection<ReserveListDto> ViewListClient(long clientId);
}
