package Chopsticks.HairHaeJoBackend.entity.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientProfileRepository extends JpaRepository<ClientProfile, Long> {

    Boolean existsByUser(User user);

    ClientProfile findByUser(User user);
}
