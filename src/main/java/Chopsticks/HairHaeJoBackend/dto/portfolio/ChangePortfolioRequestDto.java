package Chopsticks.HairHaeJoBackend.dto.portfolio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePortfolioRequestDto {
	private Long portfolioId;
	private String text;
	private String category;
	private String tag;
	private int gender;
}
