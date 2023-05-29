package Chopsticks.HairHaeJoBackend.dto.usermemo;


import Chopsticks.HairHaeJoBackend.entity.reservation.Reservation;
import Chopsticks.HairHaeJoBackend.entity.review.Review;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import Chopsticks.HairHaeJoBackend.entity.user_memo.UserMemo;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserMemoRequestDto {
    private String memo;

    public UserMemo toUserMemo(User designer, long clientId) {
        return UserMemo.builder()
            .designer(designer)
            .clientId(clientId)
            .memo(memo)
            .createdAt(LocalDateTime.now())
                .build();
    }
}

