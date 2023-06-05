package Chopsticks.HairHaeJoBackend.entity.inventory;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Designer_Inventory")
public class DesignerInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private int inventoryId;

    @Column(name = "user_id")
    private long userId;
    @Column(name="item_id")
    private int itemId;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    @OneToOne
    @JoinColumn(name="item_id",referencedColumnName = "item_id", insertable = false, updatable = false)
    private Item item;

}
