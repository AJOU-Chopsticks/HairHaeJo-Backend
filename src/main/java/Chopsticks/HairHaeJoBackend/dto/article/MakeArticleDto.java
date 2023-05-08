package Chopsticks.HairHaeJoBackend.dto.article;


import Chopsticks.HairHaeJoBackend.entity.article.Article;
import Chopsticks.HairHaeJoBackend.entity.article.Articlestate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MakeArticleDto {
    private String title;
    private String body;
    private String region;
    private String category;
    private String gender;
    private String tag;
    public Article toArticle(long writer,String before,String after) {
        return Article.builder()

                .abstractLocation(region)
                .afterImage(after)
                .beforeImage(before)
                .body(body)
                .category(category)
                .gender(gender)
                .tag(tag)
                .state(Articlestate.WATING)
                .title(title)
                .writerId(writer)
                .build();
    }
}
