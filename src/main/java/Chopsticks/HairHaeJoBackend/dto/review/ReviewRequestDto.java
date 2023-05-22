package Chopsticks.HairHaeJoBackend.dto.review;


import Chopsticks.HairHaeJoBackend.entity.reservation.Reservation;
import Chopsticks.HairHaeJoBackend.entity.review.Review;
import Chopsticks.HairHaeJoBackend.entity.user.User;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRequestDto {
    private String reviewBody;
    private int rating;
    private int reviewId;

    public Review toReview(User client, Long designer,Reservation reservationId,String reviewImage) {
        return Review.builder()
            .client(client)
            .designerId(designer)
            .reservationId(reservationId)
            .reviewImage(reviewImage)
            .createdAt(LocalDateTime.now())
            .reviewBody(reviewBody)
            .rating(rating)
                .build();
    }
}

