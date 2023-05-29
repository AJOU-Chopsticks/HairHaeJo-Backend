package Chopsticks.HairHaeJoBackend.dto.review;

import Chopsticks.HairHaeJoBackend.dto.designer.DesignerProfileSearchResponseDto;
import Chopsticks.HairHaeJoBackend.entity.designer.DesignerProfile;
import Chopsticks.HairHaeJoBackend.entity.review.Review;
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
public class ReviewSearchResponseDto {
    private String reviewImage;
    private String reviewBody;
    private int rating;
    private String reply;

    public static ReviewSearchResponseDto of(Review review){
        return ReviewSearchResponseDto.builder()
            .reviewImage(review.getReviewImage())
            .rating(review.getRating())
            .reviewBody(review.getReviewBody())
            .reply(review.getReply())
            .build();
    }



}

