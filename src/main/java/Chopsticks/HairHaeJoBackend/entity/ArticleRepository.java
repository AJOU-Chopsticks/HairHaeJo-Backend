package Chopsticks.HairHaeJoBackend.entity;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<Article, Integer> {


    @Query
            ("Select Count(*) from Article A where A.writerId=:writerId And A.state=:state")
    long thereiswrote(@Param(value="writerId")Long writerid,@Param(value="state")Articlestate state);







}
