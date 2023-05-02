package Chopsticks.HairHaeJoBackend.entity.designer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Designer_profile")
public class DesignerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private int profileId; //pk


    @Column(name="user_id")
    private Long userId; //fk

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "hair_salon_name")
    private String hairSalonName;

    @Column(name = "hair_salon_address")
    private String hairSalonAddress;

    @Column(name = "hair_salon_number")
    private String hairSalonNumber;

}