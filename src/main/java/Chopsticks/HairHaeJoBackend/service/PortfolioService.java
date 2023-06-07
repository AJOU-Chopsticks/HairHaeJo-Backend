package Chopsticks.HairHaeJoBackend.service;


import Chopsticks.HairHaeJoBackend.dto.portfolio.ChangePortfolioRequestDto;
import Chopsticks.HairHaeJoBackend.dto.portfolio.PortfolioRequestDto;
import Chopsticks.HairHaeJoBackend.dto.portfolio.PortfolioResponseDto;
import Chopsticks.HairHaeJoBackend.dto.portfolio.ViewPortfolioResponseDto;
import Chopsticks.HairHaeJoBackend.entity.designer.DesignerProfile;
import Chopsticks.HairHaeJoBackend.entity.portfolio.Portfolio;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import Chopsticks.HairHaeJoBackend.entity.user.UserRepository;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import Chopsticks.HairHaeJoBackend.entity.portfolio.PortfolioRepository;
import Chopsticks.HairHaeJoBackend.entity.designer.DesignerProfileRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final S3UploadService s3UploadService;
    private final UserRepository userRepository;
    private final DesignerProfileRepository designerProfileRepository;


    //디자이너 포트폴리오 추가
    public void save(MultipartFile image, PortfolioRequestDto requestDto)
        throws IOException {
        if (image == null || image.isEmpty()) {
            throw new RuntimeException("포트폴리오 이미지가 없습니다.");
        } else {
            portfolioRepository.save(
                requestDto.toPortfolio(getCurrentUser(), s3UploadService.upload(image)));
        }
    }

    //디자이너 포트폴리오 수정
    public void change(MultipartFile image, ChangePortfolioRequestDto requestDto)
        throws IOException {
        Portfolio portfolio = portfolioRepository.findById(requestDto.getPortfolioId())
            .orElseThrow(() -> new RuntimeException("포트폴리오 정보가 없습니다."));
        portfolio.setCategory(requestDto.getCategory());
        portfolio.setTag(requestDto.getTag());
        portfolio.setGender(requestDto.getGender());
        portfolio.setText(requestDto.getText());
        if (image != null) {
            portfolio.setImage(s3UploadService.upload(image));
        }
        portfolioRepository.save(portfolio);
    }

    //포트폴리오 상세 조회
    public ViewPortfolioResponseDto view(Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
            .orElseThrow(() -> new RuntimeException("포트폴리오 정보가 없습니다."));
        return toViewDto(portfolio);
    }

    //(디자이너) 포트폴리오 리스트 조회
    public List<PortfolioResponseDto> getDesignerPortfolios(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("디자이너 정보가 없습니다."));
        List<Portfolio> portfolios = portfolioRepository.findByDesignerId(user);
        List<PortfolioResponseDto> response = new ArrayList<>();
        for (Portfolio portfolio : portfolios) {
            response.add(toResponseDto(portfolio));
        }
        return response;
    }

    //(지역,카테고리,태그,성별) 포트폴리오 리스트 조회
    public List<PortfolioResponseDto> getByStyle(String region, String category, String tag,
        int gender) {
        List<Portfolio> portfolios = portfolioRepository.findByStyle(category, tag, gender);
        List<PortfolioResponseDto> response = new ArrayList<>();
        if (region.equals("all")) {
            for (Portfolio portfolio : portfolios) {
                response.add(toResponseDto(portfolio));
            }
        } else {
            for (Portfolio portfolio : portfolios) {
				if (isRegionMatches(region, portfolio)) {
					response.add(toResponseDto(portfolio));
				}
            }
        }
        return response;
    }

    //포트폴리오 삭제
    public void delete(Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
            .orElseThrow(() -> new RuntimeException("포트폴리오 정보가 없습니다."));
        portfolioRepository.delete(portfolio);
    }

    private User getCurrentUser() {
        User user = userRepository.findById(SecurityUtil.getCurrentMemberId())
            .orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다."));
        return user;
    }

    private boolean isRegionMatches(String region, Portfolio portfolio) {
        DesignerProfile profile = designerProfileRepository.findByUser(
                portfolio.getDesignerId());
        String address = profile.getHairSalonAddress();
        return address.contains(region);
    }

    private PortfolioResponseDto toResponseDto(Portfolio portfolio) {
        PortfolioResponseDto responseDto = PortfolioResponseDto.builder()
            .portfolioId(portfolio.getId())
            .image(portfolio.getImage())
            .build();
        return responseDto;
    }

    private ViewPortfolioResponseDto toViewDto(Portfolio portfolio) {
        DesignerProfile profile = designerProfileRepository.findByUser(
                portfolio.getDesignerId());
        ViewPortfolioResponseDto responseDto = ViewPortfolioResponseDto.builder()
            .gender(portfolio.getGender())
            .category(portfolio.getCategory())
            .designerId(portfolio.getDesignerId().getId())
            .createdAt(portfolio.getCreatedAt())
            .designerImage(portfolio.getDesignerId().getProfileImage())
            .designerName(portfolio.getDesignerId().getName())
            .hairSalonAddress(profile.getHairSalonAddress())
            .hairSalonName(profile.getHairSalonName())
            .portfolioId(portfolio.getId())
            .tag(portfolio.getTag())
            .text(portfolio.getText())
            .image(portfolio.getImage())
            .build();
        return responseDto;
    }
}
