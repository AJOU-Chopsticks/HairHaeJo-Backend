package Chopsticks.HairHaeJoBackend.entity.reservation;

import Chopsticks.HairHaeJoBackend.dto.article.ArticlelistResponseDto;
import Chopsticks.HairHaeJoBackend.dto.reservation.DesignerReserveListDto;
import Chopsticks.HairHaeJoBackend.dto.reservation.ReserveListDto;
import Chopsticks.HairHaeJoBackend.entity.article.QArticle;
import Chopsticks.HairHaeJoBackend.entity.designer.QDesignerProfile;
import Chopsticks.HairHaeJoBackend.entity.user.QUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;

public class ReservationRepositoryImpl implements ReservationRespositoryCustom {
    @Autowired
    EntityManager em;




    public Collection<DesignerReserveListDto> getDesignerReserveList(long DesignerId, short state) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QReservation Reservation=QReservation.reservation;
        QUser User=QUser.user;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if(state==1) return queryFactory.select(Projections.fields(DesignerReserveListDto.class,Reservation.id.as("reservationId"),Reservation.startTime.as("Date"),User.name.as("userName"), Reservation.tid.as("tid"), Reservation.state.as("state"), User.id.as("userId"))).from(Reservation).join(User).on(Reservation.clientId.eq(User.id)).where(Reservation.state.eq(state),Reservation.designerId.eq(DesignerId)).orderBy(Reservation.startTime.asc()).fetch();
        else return queryFactory.select(Projections.fields(DesignerReserveListDto.class,Reservation.id.as("reservationId"),Reservation.startTime.as("Date"),User.name.as("userName"), Reservation.tid.as("tid"), Reservation.state.as("state"), User.id.as("userId"))).from(Reservation).join(User).on(Reservation.clientId.eq(User.id)).where(Reservation.state.eq(state),Reservation.designerId.eq(DesignerId)).orderBy(Reservation.startTime.desc()).fetch();


    }

}
