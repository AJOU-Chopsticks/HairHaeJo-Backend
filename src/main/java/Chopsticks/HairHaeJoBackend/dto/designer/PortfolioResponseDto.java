package Chopsticks.HairHaeJoBackend.dto.designer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PortfolioResponseDto {

	private Long portfolioId;
	private String image;
}
