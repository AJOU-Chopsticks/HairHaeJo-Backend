package Chopsticks.HairHaeJoBackend.entity.inventory;

import Chopsticks.HairHaeJoBackend.entity.user.User;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private String itemName;

    @Column(name = "item_photo")
    private String itemPhoto;

    @Column(name = "item_category")
    private String itemCategory;

    @Column(name = "item_price")
    private int itemPrice;

    @Column(name = "stock")
    private int stock;
    @Column(name = "warning_stock")
    private int warningStock;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt=LocalDateTime.now();;


}
