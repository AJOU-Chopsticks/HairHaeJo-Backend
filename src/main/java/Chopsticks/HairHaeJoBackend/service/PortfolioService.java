package Chopsticks.HairHaeJoBackend.service;


import Chopsticks.HairHaeJoBackend.dto.designer.PortfolioRequestDto;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import Chopsticks.HairHaeJoBackend.entity.user.UserRepository;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import Chopsticks.HairHaeJoBackend.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PortfolioService {

	@Autowired
	private final PortfolioRepository portfolioRepository;
	private final S3UploadService s3UploadService;
	private final UserRepository userRepository;
	
	
	//디자이너 포트폴리오 추가 로직
	public void portfolioadd(MultipartFile image, PortfolioRequestDto requestDto)
			throws IOException {
		if(image.isEmpty()){
			portfolioRepository.save(requestDto.toPortfolio(getCurrentUser(),null));
		}
		else {
			portfolioRepository.save(requestDto.toPortfolio(getCurrentUser(), s3UploadService.upload(image)));
		}
	}

	//디자이너 포트폴리오 수정

	//포트폴리오 상세 조회

	//(디자이너) 포트폴리오 리스트 조회

	//(지역,카테고리,태그,성별) 포트폴리오 리스트 조회

	//포트폴리오 삭제
	pu
	private User getCurrentUser() {
		User user = userRepository.findById(SecurityUtil.getCurrentMemberId())
			.orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다."));
		return user;
	}
  
}
