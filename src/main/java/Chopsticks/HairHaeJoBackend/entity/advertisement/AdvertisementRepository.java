package Chopsticks.HairHaeJoBackend.entity.advertisement;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    List<Advertisement> findByState(int state);

    Advertisement findByTid(String tid);

    @Query(value = "select * from Advertisement where state=2 and location=:location and start_date <= :date and end_date >= :date", nativeQuery = true)
    List<Advertisement> findCurrentAdvertisement(@Param(value = "location")String location, @Param(value = "date")String date);
}
