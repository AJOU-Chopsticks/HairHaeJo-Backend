package Chopsticks.HairHaeJoBackend.entity.inventory;

import Chopsticks.HairHaeJoBackend.dto.Inventory.ChangeInventoryDto;
import Chopsticks.HairHaeJoBackend.dto.Inventory.itemViewDto;

import java.util.Collection;

public interface DesignerInventoryRepositoryCustom {
    Collection<itemViewDto> listfilter(String category, String name, boolean orderBystock, boolean orderByprice, boolean isWarning, long userid);
}
