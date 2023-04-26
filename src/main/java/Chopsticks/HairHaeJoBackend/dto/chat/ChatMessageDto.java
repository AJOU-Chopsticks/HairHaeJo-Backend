package Chopsticks.HairHaeJoBackend.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDto {

    private Long roomId;
    private String writerId;
    private String textMessage;
}
