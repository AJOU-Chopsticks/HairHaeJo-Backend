package Chopsticks.HairHaeJoBackend.entity;

import java.time.LocalDateTime;
import javax.persistence.*;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private int Id;

    @Column(name="writer_id")
    private long writerId;
    @Column(name="abstract_location")
    private String abstractLocation;

    @Column
    private String category;
    private String title;
    private String body;

    @Enumerated(EnumType.STRING)
    private Articlestate state;

    @Column(name="before_image")
    private String beforeImage;
    @Column(name="after_image")
    private String afterImage;





    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt=LocalDateTime.now();;
    public void retouching(String title,String body,String region,String Category,String beforeurl,String afterurl) {
        this.title=title;
        this.body=body;
        abstractLocation=region;

        category="-"+Category+"-";
        beforeImage=beforeurl;
        afterImage=afterurl;

    }


}
