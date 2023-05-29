package Chopsticks.HairHaeJoBackend.repository;

import Chopsticks.HairHaeJoBackend.entity.designer.DesignerProfile;
import Chopsticks.HairHaeJoBackend.entity.user.ClientProfile;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DesignerProfileRepository extends JpaRepository<DesignerProfile, Long> {
DesignerProfile findByUser_Id(Long id);

    Boolean existsByUser_Id(Long id);

    DesignerProfile findByUser(User user);

}
