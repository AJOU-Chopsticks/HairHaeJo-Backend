package Chopsticks.HairHaeJoBackend.dto.review;

import com.fasterxml.jackson.annotation.JsonSetter;
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
public class ReviewIdRequestDto {

   @JsonSetter("review_id")
   private int reviewId;

}

