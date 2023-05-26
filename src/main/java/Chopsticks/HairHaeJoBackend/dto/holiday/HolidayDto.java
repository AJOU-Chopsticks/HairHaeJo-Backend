package Chopsticks.HairHaeJoBackend.dto.holiday;

import Chopsticks.HairHaeJoBackend.entity.holiday.DesignerHoliday;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HolidayDto {
    private String holiday;

    public DesignerHoliday toholiday(long designerId) {
        return DesignerHoliday.builder()
                .designerId(designerId)
                .DesignerHoliday(holiday).build();
    }
}
