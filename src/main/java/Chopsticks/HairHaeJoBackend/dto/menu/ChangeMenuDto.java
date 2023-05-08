package Chopsticks.HairHaeJoBackend.dto.menu;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChangeMenuDto {
    int MenuId;
    String menuName;
    int menuPrice;
    String menuContent;
}
