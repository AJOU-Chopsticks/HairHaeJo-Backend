package Chopsticks.HairHaeJoBackend.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Client_profile")
public class ClientProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId; //pk

    @OneToOne
    @JoinColumn(name="profile_id")
    private User user; //fk
    
    @Column(name = "skin_type")
    private int skinType;
    
    @Column(name = "hair_type")
    private int  hairType;
    
    @Column(name = "hair_thickness")
    private int  hairThickness;
    
    @Column(name = "dyeing_history")
    private int  dyeingHistory;

    @Column(name = "decolorization_history")
    private int  decolorizationHistory;

    @Column(name = "abstract_location")
    private String  abstractLocation;

}
