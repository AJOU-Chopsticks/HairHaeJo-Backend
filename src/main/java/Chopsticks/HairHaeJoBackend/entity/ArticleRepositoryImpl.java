package Chopsticks.HairHaeJoBackend.entity;

import Chopsticks.HairHaeJoBackend.dto.article.ArticlelistResponseDto;
import Chopsticks.HairHaeJoBackend.entity.user.QUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.hibernate.criterion.Projection;
import org.springframework.beans.factory.annotation.Autowired;


import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ArticleRepositoryImpl implements ArticleRepositoryCustom {
    @Autowired
    EntityManager em;





    public Collection<ArticlelistResponseDto> listfilter(String region, String category) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QArticle Article = QArticle.article;
        QUser User= QUser.user;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(Article.abstractLocation.contains(region));

       return queryFactory.select(Projections.fields(ArticlelistResponseDto.class,User.name,Article.title,Article.Id)).from(Article).innerJoin(Article.user,User).where(Article.abstractLocation.contains(region)).fetch();
    }
}
