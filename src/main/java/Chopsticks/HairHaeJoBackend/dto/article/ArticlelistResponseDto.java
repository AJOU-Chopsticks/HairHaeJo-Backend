package Chopsticks.HairHaeJoBackend.dto.article;

import Chopsticks.HairHaeJoBackend.entity.Article;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ArticlelistResponseDto {
        @JsonSetter("userName")
        private String name;
        @JsonSetter("articleTitle")
        private String title;
        @JsonSetter("articleId")
        private int Id;



}