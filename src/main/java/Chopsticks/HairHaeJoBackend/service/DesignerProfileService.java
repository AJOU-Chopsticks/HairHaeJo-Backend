package Chopsticks.HairHaeJoBackend.service;

import Chopsticks.HairHaeJoBackend.dto.designer.ChangeDesignerProfileRequestDto;
import Chopsticks.HairHaeJoBackend.dto.designer.DesignerProfileRequestDto;
import Chopsticks.HairHaeJoBackend.dto.designer.DesignerProfileSearchResponseDto;
import Chopsticks.HairHaeJoBackend.entity.designer.DesignerProfile;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import Chopsticks.HairHaeJoBackend.entity.user.UserRepository;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import Chopsticks.HairHaeJoBackend.entity.designer.DesignerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DesignerProfileService {


	private final DesignerProfileRepository designerProfileRepository;
	private final UserRepository userRepository;

	// 디자이너 프로필 설정 로직
	public void setDesignerProfile(DesignerProfileRequestDto requestDto) {
		if (designerProfileRepository.existsByUser(getCurrentUser())) {
			throw new RuntimeException("이미 프로필이 존재합니다.");
		}
		else {
			designerProfileRepository.save(requestDto.toDesignerProfile(getCurrentUser()));
		}
	}

	private User getCurrentUser() {
		User user = userRepository.findById(SecurityUtil.getCurrentMemberId())
			.orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다."));
		return user;
	}

	//디자이너 프로필 조회 로직
	public DesignerProfileSearchResponseDto SearchDesignerProfile(Long id) {
		User user = userRepository.findById(id)
			.orElseThrow(()-> new RuntimeException("디자이너 정보가 없습니다."));
		DesignerProfile designerProfile = designerProfileRepository.findByUser(user);
		return DesignerProfileSearchResponseDto.of(designerProfile);
	}

	//디자이너 프로필 수정
	public void changeDesignerProfile (ChangeDesignerProfileRequestDto requestDto) {
		DesignerProfile designerProfile = designerProfileRepository.findByUser(getCurrentUser());
		designerProfile.setIntroduction(requestDto.getIntroduction());
		designerProfile.setHairSalonName(requestDto.getHairSalonName());
		designerProfile.setHairSalonAddress(requestDto.getHairSalonAddress());
		designerProfile.setHairSalonNumber(requestDto.getHairSalonNumber());
		designerProfileRepository.save(designerProfile);
	}
}
