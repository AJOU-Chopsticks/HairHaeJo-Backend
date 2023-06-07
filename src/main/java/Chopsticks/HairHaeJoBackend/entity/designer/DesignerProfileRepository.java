package Chopsticks.HairHaeJoBackend.entity.designer;

import Chopsticks.HairHaeJoBackend.entity.designer.DesignerProfile;
import Chopsticks.HairHaeJoBackend.entity.user.ClientProfile;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DesignerProfileRepository extends JpaRepository<DesignerProfile, Long> {

    Boolean existsByUser(User user);

    DesignerProfile findByUser(User user);

}
