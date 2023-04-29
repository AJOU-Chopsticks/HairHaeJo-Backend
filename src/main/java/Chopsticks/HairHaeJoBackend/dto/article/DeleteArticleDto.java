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
public class DeleteArticleDto {
    @JsonSetter("articleId")
    private String articleId;
}
