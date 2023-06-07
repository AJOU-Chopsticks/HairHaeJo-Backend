package Chopsticks.HairHaeJoBackend.dto.sms;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class SmsResponseDto {

	String requestId;
	LocalDateTime requestTime;
	String statusCode;
	String statusName;
}
