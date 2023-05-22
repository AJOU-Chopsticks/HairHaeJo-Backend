package Chopsticks.HairHaeJoBackend.dto.review;

import Chopsticks.HairHaeJoBackend.entity.review.Review;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import java.time.LocalDateTime;
import java.util.List;
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
public class ReviewResponseDto {

    private int reviewId;
    private Long clientId;
    private String reviewBody;
    private String reply;
    private int rating;
    private String reviewImage;





}

