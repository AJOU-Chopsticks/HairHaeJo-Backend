package Chopsticks.HairHaeJoBackend.repository;

import Chopsticks.HairHaeJoBackend.entity.user.ClientProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientProfileRepository extends JpaRepository<ClientProfile, Long> {

 
}
