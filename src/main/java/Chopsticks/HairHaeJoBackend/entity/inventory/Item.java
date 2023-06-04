package Chopsticks.HairHaeJoBackend.entity.inventory;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="item_id")
    private int itemId;

    @Column(name = "item_name")
    private int itemName;

    @Column(name = "item_content")
    private long itemContent;

    @Column(name = "item_category")
    private int itemCategory;

    @Column(name = "stock")
    private long stock;
    @Column(name = "warning_stock")
    private long warning_stock;
}
