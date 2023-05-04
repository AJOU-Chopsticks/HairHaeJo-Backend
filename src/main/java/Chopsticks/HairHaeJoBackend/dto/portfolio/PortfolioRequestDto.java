package Chopsticks.HairHaeJoBackend.dto.portfolio;

import Chopsticks.HairHaeJoBackend.entity.portfolio.Portfolio;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortfolioRequestDto {

    private String text;
    private String category;
    private String tag;
    private int gender;

    public Portfolio toPortfolio(User user, String image) {
        return Portfolio.builder()
            .designerId(user)
            .text(text)
            .category(category)
            .tag(tag)
            .gender(gender)
            .image(image)
            .build();
    }
}

