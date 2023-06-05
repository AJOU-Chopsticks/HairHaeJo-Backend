package Chopsticks.HairHaeJoBackend.entity.inventory;

import java.util.Collection;

public interface DesignerInventoryRepositoryCustom {
    Collection<Item> listfilter(String category,String name,boolean orderBystock,boolean orderByprice,boolean isWarning);
}
