package Chopsticks.HairHaeJoBackend.dto.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeArticleDto {
    private String title;
    private String body;
    private String region;
    private String category;
    private String ArticleId;
}
