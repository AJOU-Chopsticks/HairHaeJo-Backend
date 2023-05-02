package Chopsticks.HairHaeJoBackend.dto.chat;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ChatRoomResponseDto {

	private Long chatRoomId;
	private Long clientId;
	private Long designerId;
	private String clientName;
	private String designerName;
	private String clientImage;
	private String designerImage;
	private LocalDateTime updatedAt;
	private String lastMessage;
}
