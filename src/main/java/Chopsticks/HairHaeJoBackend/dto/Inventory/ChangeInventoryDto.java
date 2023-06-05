package Chopsticks.HairHaeJoBackend.dto.Inventory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeInventoryDto {
    private int itemId;
    private String itemName;
    private String itemCategory;
    private int stock;
    private int warningStock;
    private int itemPrice;
}
