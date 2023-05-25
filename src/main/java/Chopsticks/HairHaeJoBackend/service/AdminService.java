package Chopsticks.HairHaeJoBackend.service;

import Chopsticks.HairHaeJoBackend.dto.Admin.LicenseApproveRequestDto;
import Chopsticks.HairHaeJoBackend.dto.Admin.LicenseResponseDto;
import Chopsticks.HairHaeJoBackend.dto.Admin.ReportResponseDto;
import Chopsticks.HairHaeJoBackend.entity.article.Article;
import Chopsticks.HairHaeJoBackend.entity.article.ArticleRepository;
import Chopsticks.HairHaeJoBackend.entity.license.LicenseRequest;
import Chopsticks.HairHaeJoBackend.entity.license.LicenseRequestRepository;
import Chopsticks.HairHaeJoBackend.entity.report.Report;
import Chopsticks.HairHaeJoBackend.entity.report.ReportRepository;
import Chopsticks.HairHaeJoBackend.entity.user.Role;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import Chopsticks.HairHaeJoBackend.entity.user.UserRepository;
import Chopsticks.HairHaeJoBackend.repository.ReviewRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

	private final LicenseRequestRepository licenseRequestRepository;
	private final UserRepository userRepository;
	private final ReportRepository reportRepository;
	private final ArticleRepository articleRepository;
	private final ReviewRepository reviewRepository;
	private final EmailService emailService;

	public List<LicenseResponseDto> getLicenseRequests(){
		List<LicenseRequest> requests = licenseRequestRepository.findByState(0);
		List<LicenseResponseDto> responseDto = new ArrayList<>();
		for(LicenseRequest request : requests){
			responseDto.add(LicenseResponseDto.builder()
				.licenseImage(request.getImage())
				.requestId(request.getId())
				.designerId(request.getDesignerId().getId())
				.designerName(request.getDesignerId().getName())
				.build());
		}
		return responseDto;
	}

	public void approveDesigner(LicenseApproveRequestDto requestDto) throws Exception {
		LicenseRequest request = licenseRequestRepository.findById(requestDto.getRequestId())
			.orElseThrow(() -> new RuntimeException("요청 정보가 없습니다."));
		User user = request.getDesignerId();
		if(requestDto.isApprove()){
			request.setState(1);
			user.setRole(Role.ROLE_DESIGNER);
			userRepository.save(user);
			emailService.sendDesignerConfirmMessage(user.getEmail());
		}
		else {
			request.setState(2);
			emailService.sendDesignerDeniedMessage(user.getEmail());
		}
		licenseRequestRepository.save(request);
	}

	public List<ReportResponseDto> getReports(){
		List<Report> reports = reportRepository.findAll();
		List<ReportResponseDto> responseDto = new ArrayList<>();

		for(Report report : reports){
			responseDto.add(ReportResponseDto.builder()
				.reportId(report.getId())
				.reporterId(report.getReporterId().getId())
				.reporterName(report.getReporterId().getName())
				.targetUserId(report.getTargetId().getId())
				.targetName(report.getTargetId().getName())
				.reportType(report.getReportType().toString())
				.reportReason(report.getReportReason())
				.targetId(report.getObjectId())
				.build());
		}
		return responseDto;
	}

	public void deleteReport(Long reportId){
		reportRepository.deleteById(reportId);
	}

	public void deleteArticle(int articleId){
		articleRepository.deleteById(articleId);
	}

	public void deleteReview(int reviewId){
		reviewRepository.deleteById(reviewId);
	}

	public void suspendUser(Long userId){
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
		user.setSuspended(true);
		userRepository.save(user);
	}
}
