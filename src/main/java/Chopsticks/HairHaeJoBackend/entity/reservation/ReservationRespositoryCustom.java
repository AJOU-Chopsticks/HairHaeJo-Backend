package Chopsticks.HairHaeJoBackend.entity.reservation;

import Chopsticks.HairHaeJoBackend.dto.reservation.DesignerReserveListDto;

import java.util.Collection;
import java.util.List;

public interface ReservationRespositoryCustom {
    Collection<DesignerReserveListDto> getDesignerReserveList(long desinger, short state);
}
