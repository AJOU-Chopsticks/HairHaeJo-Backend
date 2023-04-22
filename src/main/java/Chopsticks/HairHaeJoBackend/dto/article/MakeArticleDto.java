package Chopsticks.HairHaeJoBackend.dto.article;

import Chopsticks.HairHaeJoBackend.dto.user.AuthResponseDto;
import Chopsticks.HairHaeJoBackend.entity.Article;
import Chopsticks.HairHaeJoBackend.entity.Articlestate;
import Chopsticks.HairHaeJoBackend.entity.Role;
import Chopsticks.HairHaeJoBackend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MakeArticleDto {
    private String title;
    private String body;
    private String beforeimage;
    private String afterimage;
    private String region;
    private String category;
    public Article toArticle(long writer,String before,String after) {
        return Article.builder()
                .writerId(writer)
                .abstractionLocation(region)
                .category(category)
                .title(title)
                .beforeImage(before)
                .afterImage(after)
                .body(body)
                .state(Articlestate.WATING)
                .build();
    }
}
