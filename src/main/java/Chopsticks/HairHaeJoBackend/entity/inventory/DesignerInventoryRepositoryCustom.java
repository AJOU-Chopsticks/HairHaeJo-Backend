package Chopsticks.HairHaeJoBackend.entity.inventory;

import Chopsticks.HairHaeJoBackend.dto.Inventory.ChangeInventoryDto;

import java.util.Collection;

public interface DesignerInventoryRepositoryCustom {
    Collection<ChangeInventoryDto> listfilter(String category, String name, boolean orderBystock, boolean orderByprice, boolean isWarning,long userid);
}
