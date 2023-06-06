package Chopsticks.HairHaeJoBackend.entity.user;

import Chopsticks.HairHaeJoBackend.entity.designer.DesignerProfile;
import Chopsticks.HairHaeJoBackend.entity.user.ClientProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientProfileRepository extends JpaRepository<ClientProfile, Long> {

    ClientProfile findByUser_Id(Long id);

    Boolean existsByUser_Id(Long id);

    ClientProfile findByUser(User user);
}
