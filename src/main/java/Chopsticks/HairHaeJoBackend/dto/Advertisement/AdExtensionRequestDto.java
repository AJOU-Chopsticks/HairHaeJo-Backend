package Chopsticks.HairHaeJoBackend.dto.Advertisement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdExtensionRequestDto {

	private Long advertiseId;
	private int extensionDays;
	private int price;
}
