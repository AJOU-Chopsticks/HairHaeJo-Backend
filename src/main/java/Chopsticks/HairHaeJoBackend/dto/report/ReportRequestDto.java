package Chopsticks.HairHaeJoBackend.dto.report;

import Chopsticks.HairHaeJoBackend.entity.report.Report.reportType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ReportRequestDto {

	private String type;
	private Long targetUserId;
	private String reason;
	private Long targetId;

	public reportType getReportType(){
		if(type.equals("CHAT")){
			return reportType.CHAT;
		} else if (type.equals("ARTICLE")){
			return reportType.ARTICLE;
		} else if (type.equals("REVIEW")){
			return reportType.REVIEW;
		} else throw new RuntimeException("올바르지 않은 신고 타입입니다.");
	}
}
