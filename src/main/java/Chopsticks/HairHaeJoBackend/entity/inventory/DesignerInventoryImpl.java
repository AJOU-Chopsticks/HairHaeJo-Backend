package Chopsticks.HairHaeJoBackend.entity.inventory;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.Collection;

public class DesignerInventoryImpl implements DesignerInventoryRepositoryCustom {
    @Autowired
    EntityManager em;


    public Collection<Item> viewInventory() {
        return null;
    }
}
