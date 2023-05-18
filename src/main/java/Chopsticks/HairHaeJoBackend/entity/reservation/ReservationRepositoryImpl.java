package Chopsticks.HairHaeJoBackend.entity.reservation;

import Chopsticks.HairHaeJoBackend.dto.article.ArticlelistResponseDto;
import Chopsticks.HairHaeJoBackend.dto.reservation.ReserveListDto;
import Chopsticks.HairHaeJoBackend.entity.article.QArticle;
import Chopsticks.HairHaeJoBackend.entity.user.QUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.Collection;

public class ReservationRepositoryImpl {
    @Autowired
    EntityManager em;





    public Collection<ArticlelistResponseDto> listfilter(String region, String category, String gender, String tag) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QArticle Article = QArticle.article;
        QUser User= QUser.user;
        BooleanBuilder booleanBuilder = new BooleanBuilder();


        return queryFactory.select(Projections.fields(ReserveListDto.class,User.name).from(Article).innerJoin(Article.user,User).where(booleanBuilder).fetch();
    }

}
