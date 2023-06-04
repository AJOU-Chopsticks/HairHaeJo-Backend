package Chopsticks.HairHaeJoBackend.dto.Inventory;

import Chopsticks.HairHaeJoBackend.entity.article.Article;
import Chopsticks.HairHaeJoBackend.entity.article.Articlestate;
import Chopsticks.HairHaeJoBackend.entity.inventory.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MakeInventoryDto {



    private String itemName;
    private String itemCategory;
    private int stock;
    private int warningStock;
    private int itemPrice;

    public Item toItem(String image) {
        return Item.builder().itemName(itemName)

                .itemCategory(itemCategory)
                .itemPhoto(image)
                .stock(stock)
                .warning_stock(warningStock)
                .itemPrice(itemPrice)
                .build();
    }



}
