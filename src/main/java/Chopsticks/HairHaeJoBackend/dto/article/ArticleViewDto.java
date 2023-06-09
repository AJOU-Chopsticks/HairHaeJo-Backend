package Chopsticks.HairHaeJoBackend.dto.article;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleViewDto {

    private String title;
    private String body;
    private String beforeimage;
    private String afterimage;
    private String region;
    private String category;
    private String gender;
    private String tag;
    private String userName;
    private String profileImage;
    private Long userId;
}
