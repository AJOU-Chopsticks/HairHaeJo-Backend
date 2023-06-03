package Chopsticks.HairHaeJoBackend.dto.Advertisement;

import Chopsticks.HairHaeJoBackend.entity.advertisement.Advertisement;
import Chopsticks.HairHaeJoBackend.entity.reservation.Reservation;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@ToString
public class AdvertisementRequestDto {

    private String title;
    private String text;
    private int price;
    private String location;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    public Advertisement toAdvertisement(User user, String image, String tid){
        return Advertisement.builder()
            .advertiserId(user)
            .tid(tid)
            .title(title)
            .image(image)
            .text(text)
            .adPrice(price)
            .location(location)
            .startDate(startDate)
            .endDate(endDate)
            .state(0)
            .build();
    }
}
