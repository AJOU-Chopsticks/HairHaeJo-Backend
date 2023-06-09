package Chopsticks.HairHaeJoBackend.dto.chat;

import Chopsticks.HairHaeJoBackend.entity.chat.ChatMessage.Type;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ChatMessageResponseDto {

	private Type type;
	private String writerName;
	private String text;
	private String image;
	private LocalDateTime createdAt;
}
