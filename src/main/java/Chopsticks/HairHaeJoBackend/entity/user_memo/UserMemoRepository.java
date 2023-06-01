package Chopsticks.HairHaeJoBackend.entity.user_memo;


import Chopsticks.HairHaeJoBackend.entity.reservation.Reservation;
import Chopsticks.HairHaeJoBackend.entity.review.Review;
import Chopsticks.HairHaeJoBackend.entity.user_memo.UserMemo;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMemoRepository extends JpaRepository<UserMemo, Integer> {

    Boolean existsByClientId(Long id);
    Boolean existsByDesigner(User uid);
    List<UserMemo> findByClientIdAndDesigner(Long id ,User uid);

}
