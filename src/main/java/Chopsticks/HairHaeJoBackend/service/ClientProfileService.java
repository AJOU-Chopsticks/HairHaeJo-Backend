package Chopsticks.HairHaeJoBackend.service;

import Chopsticks.HairHaeJoBackend.dto.user.ChangeClientProfileRequestDto;
import Chopsticks.HairHaeJoBackend.dto.user.ClientProfileRequestDto;
import Chopsticks.HairHaeJoBackend.dto.user.ClientProfileSearchResponseDto;
import Chopsticks.HairHaeJoBackend.entity.user.ClientProfile;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import Chopsticks.HairHaeJoBackend.entity.user.UserRepository;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import Chopsticks.HairHaeJoBackend.entity.user.ClientProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientProfileService {

	private final ClientProfileRepository clientProfileRepository;
	private final UserRepository userRepository;
	
	
	//유저 초기 프로필 설정 로직
	public void defaultClientProfile (ClientProfileRequestDto requestDto) {
		if (clientProfileRepository.existsByUser(getCurrentUser())) {
			throw new RuntimeException("이미 프로필이 존재합니다.");
		}
			clientProfileRepository.save(requestDto.toClientProfile(getCurrentUser()));
	}

	private User getCurrentUser() {
		User user = userRepository.findById(SecurityUtil.getCurrentMemberId())
			.orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다."));
		return user;
	}


	//유저 프로필 조회 로직
	public ClientProfileSearchResponseDto SearchClientProfile(Long id) {
		User user = userRepository.findById(id)
			.orElseThrow(()-> new RuntimeException("고객 프로필 정보가 없습니다."));
		ClientProfile clientProfile = clientProfileRepository.findByUser(user);
		return ClientProfileSearchResponseDto.of(clientProfile);
	}

	//유저 프로필 수정

	public void changeClientProfile (ChangeClientProfileRequestDto requestDto) {
		ClientProfile clientProfile = clientProfileRepository.findByUser(getCurrentUser());

		clientProfile.setSkinType(requestDto.getSkinType());
		clientProfile.setHairType(requestDto.getHairType());
		clientProfile.setHairThickness(requestDto.getHairThickness());
		clientProfile.setDyeingHistory(requestDto.getDyeingHistory());
		clientProfile.setDecolorizationHistory(requestDto.getDecolorizationHistory());
		clientProfile.setAbstractLocation(requestDto.getAbstractLocation());
		clientProfileRepository.save(clientProfile);
	}

}
