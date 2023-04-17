package Chopsticks.HairHaeJoBackend.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "User")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private int Id;

    @Column(name="writer_id")
    private long writerId;
    @Column(name="abstraction_location")
    private String abstractionLocation;
    @Column
    private String category;
    private String title;
    private String body;
    @Column(name="before_image")
    private String beforeImage;
    @Column(name="after_image")
    private String afterImage;
    @Enumerated(EnumType.STRING)
    private Articlestate state;


    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    @CreationTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
