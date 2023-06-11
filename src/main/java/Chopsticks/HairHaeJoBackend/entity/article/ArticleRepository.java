package Chopsticks.HairHaeJoBackend.entity.article;

import java.util.Collection;

import Chopsticks.HairHaeJoBackend.dto.article.ArticleViewDto;
import Chopsticks.HairHaeJoBackend.dto.article.ArticlelistResponseDto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer>, ArticleRepositoryCustom {




    @Query
            ("Select Count(*) from Article A where A.writerId=:writerId And A.state=:state")
    long thereiswrote(@Param(value="writerId")Long writerid,@Param(value="state") Articlestate state);

    @Query(value = "SELECT distinct new Chopsticks.HairHaeJoBackend.dto.article.ArticlelistResponseDto(B.name,A.title,A.Id,A.abstractLocation,A.category,A.gender,A.tag,B.profileImage)" +
            " FROM Article A JOIN A.user B " +
            "WHERE A.writerId=:writerId")
    Collection<ArticlelistResponseDto>  myarticle(@Param("writerId")long writerId);







    @Query(value = "SELECT distinct new Chopsticks.HairHaeJoBackend.dto.article.ArticlelistResponseDto(B.name,A.title,A.Id,A.abstractLocation,A.category,A.gender,A.tag,B.profileImage)" +
            " FROM Article A JOIN A.user B " +
            "WHERE A.title like %:title%"
            )
    Collection<ArticlelistResponseDto> findkeyword(@Param("title") String title);

    @Query(value="SELECT new Chopsticks.HairHaeJoBackend.dto.article.ArticleViewDto(A.title,A.body,A.beforeImage,A.afterImage,A.abstractLocation,A.category,A.gender,A.tag,B.name,B.profileImage,B.id) " +
            "FROM Article A JOIN A.user B" +
            " WHERE A.Id=:articleid ORDER BY A.updatedAt DESC")
    ArticleViewDto viewArticle(@Param(value="articleid") int id);


    

}
