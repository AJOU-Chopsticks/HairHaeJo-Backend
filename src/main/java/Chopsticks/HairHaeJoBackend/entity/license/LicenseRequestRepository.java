package Chopsticks.HairHaeJoBackend.entity.license;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseRequestRepository extends JpaRepository<LicenseRequest, Long> {

	List<LicenseRequest> findByState(int state);
}
