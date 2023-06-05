package Chopsticks.HairHaeJoBackend.entity.article;

import Chopsticks.HairHaeJoBackend.dto.article.ArticlelistResponseDto;

import Chopsticks.HairHaeJoBackend.entity.user.QUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;


import javax.persistence.EntityManager;
import java.util.Collection;

public class ArticleRepositoryImpl implements ArticleRepositoryCustom {
    @Autowired
    EntityManager em;





    public Collection<ArticlelistResponseDto> listfilter(String region, String category,String gender,String tag) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QArticle Article = QArticle.article;
        QUser User= QUser.user;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if(!region.equals("all"))booleanBuilder.and(Article.abstractLocation.contains(region));
        if(!category.equals("all")) booleanBuilder.and(Article.category.contains(category));
        if(!gender.equals("all")) booleanBuilder.and(Article.gender.contains(gender));
        if(!tag.equals("all")) booleanBuilder.and(Article.tag.contains(tag));

       return queryFactory.select(Projections.fields(ArticlelistResponseDto.class,User.name.as("userName"),Article.title.as("articleTitle"),Article.Id.as("articleId"),Article.abstractLocation.as("region"),Article.category,Article.gender,Article.tag,User.profileImage)).from(Article).innerJoin(Article.user,User).where(booleanBuilder).orderBy(Article.updatedAt.desc()).fetch();
    }
}
