package Chopsticks.HairHaeJoBackend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class APIMessages {

    private boolean success;
    private String message;
    private Object data;

    @Builder
    public APIMessages(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
