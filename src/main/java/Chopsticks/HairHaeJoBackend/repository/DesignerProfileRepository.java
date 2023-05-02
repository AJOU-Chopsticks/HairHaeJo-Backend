package Chopsticks.HairHaeJoBackend.repository;

import Chopsticks.HairHaeJoBackend.entity.designer.DesignerProfile;
import Chopsticks.HairHaeJoBackend.entity.designer.DesignerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignerProfileRepository extends JpaRepository<DesignerProfile, Long> {

}
