package Chopsticks.HairHaeJoBackend.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageRequestDto {

    private Long roomId;
    private Long writerId;
    private String text;
    private String image;
}
