package Chopsticks.HairHaeJoBackend.entity.designer;

import Chopsticks.HairHaeJoBackend.entity.designer.DesignerProfile;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignerRecommendRepository extends JpaRepository<DesignerProfile, Long> {

    @Query(value = "select user_id from Designer_profile where hair_salon_address like %:region%", nativeQuery = true)
    List<Long> getDesignersByRegion(String region);

    // 예약 횟수
    @Query(value = "SELECT designer_id, COUNT(designer_id) AS reservation_count "
        + "FROM (SELECT designer_id "
        + "FROM Designer_profile P "
        + "INNER JOIN (SELECT R.designer_id "
        + "FROM User U "
        + "INNER JOIN Reservation R ON U.user_id = R.client_id "
        + "WHERE U.gender = :gender AND ABS(U.age - :age) <= 3 AND R.state = 2 AND TIMESTAMPDIFF(MONTH, R.updated_at, NOW()) <= 12) S ON P.user_id = S.designer_id "
        + "WHERE P.hair_salon_address LIKE %:region%) tb "
        + "GROUP BY designer_id", nativeQuery = true)
    List<Object[]> countReservation(int gender, int age, String region);

    //관심 디자이너 등록 횟수
    @Query(value = "SELECT designer_id, COUNT(designer_id) AS like_count "
        + "FROM (SELECT designer_id "
        + "FROM Designer_profile P INNER JOIN "
        + "(SELECT D.designer_id "
        + "FROM User U INNER JOIN Designer_like D "
        + "ON U.user_id=D.client_id "
        + "WHERE U.gender=:gender AND ABS(U.age-:age)<=3 AND timestampdiff(MONTH, D.created_at, now()) <= 12) L "
        + "ON P.user_id=L.designer_id "
        + "WHERE P.hair_salon_address LIKE %:region%)tb "
        + "GROUP BY designer_id", nativeQuery = true)
    List<Object[]> countLike(int gender, int age, String region);


    //별점5점 리뷰 횟수
    @Query(value = "SELECT designer_id, COUNT(designer_id) AS review_count "
        + "FROM (SELECT L.designer_id "
        + "FROM Designer_profile P INNER JOIN "
        + "(SELECT R.designer_id "
        + "FROM User U INNER JOIN "
        + "(SELECT A.client_id, A.designer_id "
        + "FROM Reservation A INNER JOIN Review B "
        + "ON A.reservation_id=B.reservation_id "
        + "WHERE B.rating=5 AND timestampdiff(MONTH, B.created_at, now())<=12) R "
        + "ON U.user_id=R.client_id "
        + "WHERE U.gender=:gender AND ABS(U.age-:age)<=3) L "
        + "ON P.user_id=L.designer_id "
        + "WHERE P.hair_salon_address LIKE %:region%)tb "
        + "GROUP BY designer_id", nativeQuery = true)
    List<Object[]> countReview(int gender, int age, String region);
}
