package Chopsticks.HairHaeJoBackend.entity.user_memo;

import Chopsticks.HairHaeJoBackend.entity.user.User;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "User_memo")
public class UserMemo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memo_id")
    private int memoId; //pk

    @ManyToOne
    @JoinColumn(name = "designer_id")
    private  User designer; //fk

    @ManyToOne
    @JoinColumn(name = "client_id",insertable = false,updatable = false)
    private User client; //fk

    @Column(name = "client_id")
    private long clientId;

    @Column(name = "memo")
    private String memo;

    @Column(name = "created_at")
    private  LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();






}

