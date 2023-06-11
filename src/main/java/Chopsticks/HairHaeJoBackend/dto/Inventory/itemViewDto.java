package Chopsticks.HairHaeJoBackend.dto.Inventory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class itemViewDto {
    private int itemId;
    private String itemName;
    private String itemCategory;

    private String itemPhoto;
    private int stock;
    private int warningStock;
    private int itemPrice;
}
