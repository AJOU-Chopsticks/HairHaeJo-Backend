package Chopsticks.HairHaeJoBackend.dto.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeMenuDto {
    int  menuId;
    String menuName;
    int menuPrice;
    String menuContent;
}
