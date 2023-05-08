package Chopsticks.HairHaeJoBackend.entity.designer;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Designer_profile")
public class DesignerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId; //pk

    @OneToOne
    @JoinColumn(name = "uid")
    private User user; //fk

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "hair_salon_name")
    private String hairSalonName;

    @Column(name = "hair_salon_address")
    private String hairSalonAddress;

    @Column(name = "hair_salon_number")
    private String hairSalonNumber;
}

