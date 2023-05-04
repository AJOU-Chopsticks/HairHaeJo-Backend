package Chopsticks.HairHaeJoBackend.dto.designer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ChangePortfolioRequestDto {
	private Long portfolioId;
	private String text;
	private String category;
	private String tag;
	private int gender;
}
