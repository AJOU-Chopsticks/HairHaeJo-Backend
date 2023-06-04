package Chopsticks.HairHaeJoBackend.entity.inventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignerInventoryRepository extends JpaRepository<DesignerInventory,Integer>,DesignerInventoryRepositoryCustom {
}
