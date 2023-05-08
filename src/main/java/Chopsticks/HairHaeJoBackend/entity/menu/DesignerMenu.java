package Chopsticks.HairHaeJoBackend.entity.menu;

import Chopsticks.HairHaeJoBackend.entity.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder

@Table(name = "Designer_menu")
public class DesignerMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    int Id;
    @Column(name="designer_id")
    long designerId;
    @Column(name="menu_name")
    String menuName;
    @Column(name="menu_price")
    int menuPrice;
    @Column(name="menu_content")
    String menuContent;
    @ManyToOne
    @JoinColumn(name="designer_id",referencedColumnName = "user_id", insertable = false, updatable = false)
    private User user;

}
