package Chopsticks.HairHaeJoBackend.entity;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    public Collection<Article> findByabstractionLocation(String abstractionLocation);

    public Collection<Article> findBycategoryContaining(List<String> category);
    public Collection<Article> findIdAndwriterIdAndtitleBytitleContaining(String title);
    @Modifying // select 문이 아님을 나타낸다
    @Query(value = "UPDATE Article A set A.title = :title, A.body = :body,A.before_image=:beforeimage, A.after_image=:afterimage,A.abstraction_location=:location,A.category=:category where A.writer_id = :writer_id And A.state=:state", nativeQuery = true)
    void changeArticle(@Param("title")String title, @Param("body")String body,
                   @Param("beforeimage")String beforeimage,@Param("afterimage")String afterimage,
                   @Param("location")String location,@Param("category")String category,
                   @Param("writer_id")Long writer_id,@Param("state")Articlestate state) throws IOException;
}
