package Chopsticks.HairHaeJoBackend.dto.designer;

import Chopsticks.HairHaeJoBackend.entity.designer.Portfolio;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
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
    private String type;
    private  Long profileId;
    public Portfolio toPortfolio(String image) {
        return Portfolio.builder()
            .profileId(SecurityUtil.getCurrentMemberId())
                .text(text)
                .type(type)
                .image(image)
                .build();
    }
}

