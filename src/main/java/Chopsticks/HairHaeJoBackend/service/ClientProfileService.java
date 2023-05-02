package Chopsticks.HairHaeJoBackend.service;

import Chopsticks.HairHaeJoBackend.dto.user.ClientProfileRequestDto;
import Chopsticks.HairHaeJoBackend.repository.ClientProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientProfileService {

	private final ClientProfileRepository clientProfileRepository;
	
	
	//초기 프로필 설정 로직
	public void defaultClientProfile (ClientProfileRequestDto requestDto) {

			clientProfileRepository.save(requestDto.toClientProfile());
	}


	//유저 프로필 조회 로직



}
