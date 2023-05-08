package Chopsticks.HairHaeJoBackend.dto.menu;

import Chopsticks.HairHaeJoBackend.entity.article.Article;
import Chopsticks.HairHaeJoBackend.entity.article.Articlestate;
import Chopsticks.HairHaeJoBackend.entity.menu.DesignerMenu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MakeMenuDto {
    String menuName;
    int menuPrice;
    String menuContent;
    public DesignerMenu toDesignerMenu(long writer) {
        return DesignerMenu.builder()
                .designerId(writer)
                .menuName(menuName)
                .menuPrice(menuPrice)
                .menuContent(menuContent)
                .build();
    }
}
