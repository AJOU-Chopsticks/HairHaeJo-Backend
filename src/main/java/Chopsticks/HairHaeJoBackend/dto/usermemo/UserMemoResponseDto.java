package Chopsticks.HairHaeJoBackend.dto.usermemo;

import Chopsticks.HairHaeJoBackend.dto.designer.DesignerProfileSearchResponseDto;
import Chopsticks.HairHaeJoBackend.entity.user_memo.UserMemo;
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
public class UserMemoResponseDto {

    private int memoId;
    private String memo;

    public static UserMemoResponseDto of(UserMemo userMemo){
        return UserMemoResponseDto.builder()
            .memo(userMemo.getMemo())
            .build();
    }
}

