package Chopsticks.HairHaeJoBackend.dto.chat;

import Chopsticks.HairHaeJoBackend.entity.chat.ChatMessage.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageRequestDto {

    private Type type;
    private Long roomId;
    private Long writerId;
    private String text;
    private String image;
}
