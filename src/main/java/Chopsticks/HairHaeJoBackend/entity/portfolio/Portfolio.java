package Chopsticks.HairHaeJoBackend.entity.portfolio;


import Chopsticks.HairHaeJoBackend.entity.user.User;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Portfolio")
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_id")
    private Long id; //pk

    @ManyToOne
    @JoinColumn(name="designer_id")
    private User designerId; //fk
    
    @Column(name = "image")
    private String image;
    
    @Column(name = "category")
    private String category;

    @Column(name = "tag")
    private String tag;

    @Column(name = "gender")
    private int gender;
    
    @Column(name = "text")
    private String text;
    
    @CreationTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
   
}
