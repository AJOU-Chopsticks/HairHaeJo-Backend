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





    public Collection<ArticlelistResponseDto> listfilter(String region, String category,String gender,String tag) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QArticle Article = QArticle.article;
        QUser User= QUser.user;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(Article.abstractLocation.contains(region));
        if(category!=null) booleanBuilder.and(Article.category.contains(category));
        if(gender!=null) booleanBuilder.and(Article.gender.contains(gender));
        if(tag!=null) booleanBuilder.and(Article.tag.contains(tag));

       return queryFactory.select(Projections.fields(ArticlelistResponseDto.class,User.name.as("userName"),Article.title.as("articleTitle"),Article.Id.as("articleId"),Article.abstractLocation.as("region"),Article.category,Article.gender,Article.tag,User.profileImage)).from(Article).innerJoin(Article.user,User).where(booleanBuilder).fetch();
    }
}
