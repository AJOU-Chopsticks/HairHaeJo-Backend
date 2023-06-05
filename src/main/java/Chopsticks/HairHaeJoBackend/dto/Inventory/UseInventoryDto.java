package Chopsticks.HairHaeJoBackend.dto.Inventory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UseInventoryDto {
    int itemId;
    int stock;
}
