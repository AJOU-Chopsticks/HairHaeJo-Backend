package Chopsticks.HairHaeJoBackend.dto.designer;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ViewPortfolioResponseDto {
	private Long portfolioId;
	private Long designerId;
	private String designerName;
	private String designerImage;
	private String image;
	private String text;
	private String category;
	private String tag;
	private int gender;
	private LocalDateTime createdAt;
}
