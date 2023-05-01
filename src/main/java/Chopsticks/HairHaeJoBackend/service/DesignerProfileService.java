package Chopsticks.HairHaeJoBackend.service;

import Chopsticks.HairHaeJoBackend.dto.designer.DesignerProfileRequestDto;
import Chopsticks.HairHaeJoBackend.repository.DesignerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DesignerProfileService {

	@Autowired
	private final DesignerProfileRepository designerProfileRepository;
	
	
    // 디자이너 프로필 설정 로직
	public void defaultdesignerprofile(DesignerProfileRequestDto requestDto) {

		designerProfileRepository.save(requestDto.toDesignerProfile());
	}






  
}
