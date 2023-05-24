package Chopsticks.HairHaeJoBackend.dto.Admin;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReportResponseDto {

    private Long reportId;
    private Long reporterId;
    private String reporterName;
    private Long targetId;
    private String targetName;
    private String reportType;
    private String reportReason;
}
