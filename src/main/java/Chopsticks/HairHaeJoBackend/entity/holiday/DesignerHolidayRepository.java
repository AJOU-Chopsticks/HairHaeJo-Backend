package Chopsticks.HairHaeJoBackend.entity.holiday;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignerHolidayRepository extends JpaRepository<DesignerHoliday,Integer> {
    DesignerHoliday findBydesignerId(long designerId);
}
