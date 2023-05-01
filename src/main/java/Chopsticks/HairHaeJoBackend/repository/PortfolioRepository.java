package Chopsticks.HairHaeJoBackend.repository;

import Chopsticks.HairHaeJoBackend.entity.designer.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository <Portfolio, Long> {

  

}
