package Chopsticks.HairHaeJoBackend.dto.article;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ArticlelistResponseDto {
        @JsonSetter("userName")
        private String userName;
        @JsonSetter("articleTitle")
        private String articleTitle;

        @JsonSetter("articleId")
        private int articleId;
        @JsonSetter("region")
        private String region;
        @JsonSetter("category")
        private String category;
        @JsonSetter("gender")
        private String gender;
        @JsonSetter("tag")
        private String tag;
        @JsonSetter("profielImage")
        private String profileImage;




}
