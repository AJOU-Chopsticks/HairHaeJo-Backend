package Chopsticks.HairHaeJoBackend.service;


import Chopsticks.HairHaeJoBackend.dto.designer.PortfolioRequestDto;
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
	
	
	//디자이너 포트폴리오 추가 로직
	public void portfolioadd(MultipartFile image, PortfolioRequestDto requestDto)
			throws IOException {

		if(image.isEmpty()){
			portfolioRepository.save(requestDto.toPortfolio(null));
		}
		else {
			portfolioRepository.save(requestDto.toPortfolio(s3UploadService.upload(image)));
		}

	}
	
  
}
