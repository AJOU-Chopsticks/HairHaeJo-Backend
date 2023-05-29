package Chopsticks.HairHaeJoBackend.entity.holiday;

import Chopsticks.HairHaeJoBackend.entity.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Designer_holiday")
public class DesignerHoliday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "holiday_id")
    private int Id;
    @Column(name="designer_id")
    private long designerId;
    @Column(name = "Designer_holiday")
    private String DesignerHoliday;



}
