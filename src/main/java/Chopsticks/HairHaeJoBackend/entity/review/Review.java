package Chopsticks.HairHaeJoBackend.entity.review;

import Chopsticks.HairHaeJoBackend.entity.reservation.Reservation;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private int reviewId; //pk

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private  Reservation reservationId; //fk

    @Column(name = "designer_id")
    private Long designerId;

    @Column(name = "review_image")
    private String reviewImage;

    @Column(name = "review_body")
    private String reviewBody;

    @Column(name = "rating")
    private int rating;

    @Column(name = "reply")
    private String reply;

    @Column(name = "created_at")
    private  LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name="designer_id", insertable = false, updatable = false)
    private User designer; //디자이너 아이디

    @ManyToOne
    @JoinColumn(name="client_id")
    private User client; //고객 아이디




}

