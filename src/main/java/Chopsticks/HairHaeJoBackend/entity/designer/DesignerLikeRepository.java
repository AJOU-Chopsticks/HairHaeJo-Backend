package Chopsticks.HairHaeJoBackend.entity.designer;

import Chopsticks.HairHaeJoBackend.entity.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignerLikeRepository extends JpaRepository<DesignerLike, Long> {

	Boolean existsByClientIdAndDesignerId(User client, User designer);

	void deleteByClientIdAndDesignerId(User client, User designer);

	List<DesignerLike> findByClientId(User client);
}
