package Chopsticks.HairHaeJoBackend.entity.menu;

import Chopsticks.HairHaeJoBackend.entity.user.User;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Designer_menu")
public class DesignerMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private int menuId;

    @Column(name="designer_id")
    private long designerId;

    @Column(name="menu_name")
    private String menuName;

    @Column(name="menu_price")
    private int menuPrice;

    @Column(name="menu_content")
    private String menuContent;


}
