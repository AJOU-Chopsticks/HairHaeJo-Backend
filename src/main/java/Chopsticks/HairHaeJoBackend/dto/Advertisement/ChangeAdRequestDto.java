package Chopsticks.HairHaeJoBackend.dto.Advertisement;

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
public class ChangeAdRequestDto {

    private Long advertiseId;
    private String title;
    private String text;
    private String location;
}
