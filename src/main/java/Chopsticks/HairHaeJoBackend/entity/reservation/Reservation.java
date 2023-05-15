package Chopsticks.HairHaeJoBackend.entity.reservation;


import Chopsticks.HairHaeJoBackend.entity.user.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reservation_id")
    private int id;
    @Column(name="designer_id")
    private long designerId;
    @Column(name="client_id")
    private long clientId;
    @Column(name="start_time")
    private LocalDateTime startTime;
    @Column(name="end_time")
    private LocalDateTime endTime;
    @Column(name="menu_id")
    private int menuId;
    @Column
    private short state;




    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt=LocalDateTime.now();
    @Column(name="tid")
    private String tid;
    @ManyToOne
    @JoinColumn(name="designer_id",referencedColumnName = "user_id", insertable = false, updatable = false)
    private User user;











}
