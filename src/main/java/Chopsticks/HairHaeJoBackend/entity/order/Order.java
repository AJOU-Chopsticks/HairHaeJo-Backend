package Chopsticks.HairHaeJoBackend.entity.order;

import Chopsticks.HairHaeJoBackend.entity.article.Articlestate;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "\"Order\"")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"order_id\"")
    private int Id;
    @Column
    private String tid;
    @Column(name="total_amount")
    private int totalAmount;
    @Column(name="tax_free_amount")
    private int taxFree;
    @Column(name="user_id")
    private long userId;
    @Column(name="menu_id")
    private int menuId;
    @Column
    @Enumerated(EnumType.STRING)
    private OrderState state;
}
