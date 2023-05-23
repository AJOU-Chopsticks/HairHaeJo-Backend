package Chopsticks.HairHaeJoBackend.entity.reservation;

import Chopsticks.HairHaeJoBackend.dto.article.ArticlelistResponseDto;
import Chopsticks.HairHaeJoBackend.dto.reservation.DesignerReserveListDto;
import Chopsticks.HairHaeJoBackend.dto.reservation.ReserveListDto;
import Chopsticks.HairHaeJoBackend.entity.article.QArticle;
import Chopsticks.HairHaeJoBackend.entity.designer.QDesignerProfile;
import Chopsticks.HairHaeJoBackend.entity.menu.QDesignerMenu;
import Chopsticks.HairHaeJoBackend.entity.review.QReview;
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
        QDesignerMenu Menu=QDesignerMenu.designerMenu;
        QReview Review= QReview.review;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if(state==1) return queryFactory.select(Projections.fields(DesignerReserveListDto.class,Reservation.id.as("reservationId"),Reservation.startTime.as("Date"),Menu.menuName.as("menuName"),User.name.as("userName"), Reservation.tid.as("tid"), Reservation.state.as("state"), User.id.as("userId"),Review.reviewId.coalesce(-1).as("reviewId"))).distinct().from(Reservation)
                .join(User).on(Reservation.clientId.eq(User.id))
                .join(Menu).on(Reservation.menuId.eq(Menu.menuId))
                .leftJoin(Review).on(Reservation.id.eq(Review.reservationId.id))
                .where(Reservation.state.eq(state),Reservation.designerId.eq(DesignerId)).orderBy(Reservation.startTime.asc()).fetch();
        else return queryFactory.select(Projections.fields(DesignerReserveListDto.class,Reservation.id.as("reservationId"),Reservation.startTime.as("Date"),Menu.menuName.as("menuName"),User.name.as("userName"), Reservation.tid.as("tid"), Reservation.state.as("state"), User.id.as("userId"),Review.reviewId.coalesce(-1).as("reviewId"))).distinct().from(Reservation)
                .join(User).on(Reservation.clientId.eq(User.id))
                .join(Menu).on(Reservation.menuId.eq(Menu.menuId))
                .leftJoin(Review).on(Reservation.id.eq(Review.reservationId.id))
                .where(Reservation.state.eq(state),Reservation.designerId.eq(DesignerId)).orderBy(Reservation.startTime.desc()).fetch();


    }
    public Collection<ReserveListDto> ViewListClient(long clientId) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QReservation Reservation=QReservation.reservation;
        QUser User=QUser.user;
        QDesignerMenu Menu=QDesignerMenu.designerMenu;
        QDesignerProfile Profile=QDesignerProfile.designerProfile;
        QReview Review= QReview.review;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.or(Reservation.state.eq((short)1));
        booleanBuilder.or(Reservation.state.eq((short)2));
        return queryFactory.select(Projections.fields(ReserveListDto.class,Reservation.id.as("reservationId"),Reservation.startTime.as("Date"),Menu.menuName.as("menuName"),Profile.hairSalonAddress.as("location"),User.name.as("userName"), Reservation.tid.as("tid"), Reservation.state.as("state"), User.id.as("userId"),Review.reviewId.coalesce(-1).as("reviewId")))
                .from(Reservation).join(User).on(Reservation.designerId.eq(User.id))
                .join(Menu).on(Reservation.menuId.eq(Menu.menuId))
                .join(Profile).on(Profile.userId.eq(User.id))
                .leftJoin(Review).on(Reservation.id.eq(Review.reservationId.id))
                .where(booleanBuilder,Reservation.clientId.eq(clientId)).orderBy(Reservation.startTime.desc()).fetch();

    }


}
