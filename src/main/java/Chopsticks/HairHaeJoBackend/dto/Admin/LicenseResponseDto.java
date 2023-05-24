package Chopsticks.HairHaeJoBackend.dto.Admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LicenseResponseDto {

	private Long requestId;
	private Long designerId;
	private String licenseImage;
	private String designerName;
}
