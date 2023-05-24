package Chopsticks.HairHaeJoBackend.entity.user;

import java.time.LocalDateTime;
import javax.persistence.*;

import Chopsticks.HairHaeJoBackend.entity.designer.DesignerProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column
    private String email;
    private String password;
    private String name;
    private int gender;
    private int age;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "fcm_token")
    private String fcmToken;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "suspended")
    private boolean suspended;

    @OneToOne
    @JoinColumn(name="user_id",referencedColumnName = "user_id", insertable = false, updatable = false)
    private DesignerProfile designerProfile;

}
