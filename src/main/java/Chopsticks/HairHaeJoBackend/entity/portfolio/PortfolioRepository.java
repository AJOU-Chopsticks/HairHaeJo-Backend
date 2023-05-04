package Chopsticks.HairHaeJoBackend.entity.portfolio;

import Chopsticks.HairHaeJoBackend.entity.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository <Portfolio, Long> {

	List<Portfolio> findByDesignerId(User userId);
}
