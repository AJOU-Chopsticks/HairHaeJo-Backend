package Chopsticks.HairHaeJoBackend.dto.reservation;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClientListResponseDto {

	private Long clientId;
	private String clientName;
	private String profileImage;
	private String visitCount;
	private String recentVisit;
}
