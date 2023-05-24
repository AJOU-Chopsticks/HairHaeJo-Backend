package Chopsticks.HairHaeJoBackend.service;

import Chopsticks.HairHaeJoBackend.dto.Admin.LicenseApproveRequestDto;
import Chopsticks.HairHaeJoBackend.dto.Admin.LicenseResponseDto;
import Chopsticks.HairHaeJoBackend.entity.license.LicenseRequest;
import Chopsticks.HairHaeJoBackend.entity.license.LicenseRequestRepository;
import Chopsticks.HairHaeJoBackend.entity.user.Role;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import Chopsticks.HairHaeJoBackend.entity.user.UserRepository;
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

	public void approveDesigner(LicenseApproveRequestDto requestDto){
		LicenseRequest request = licenseRequestRepository.findById(requestDto.getDesignerId())
			.orElseThrow(() -> new RuntimeException("요청 정보가 없습니다."));
		if(requestDto.isApprove()){
			request.setState(1);
			User user = userRepository.findById(requestDto.getDesignerId())
				.orElseThrow(() -> new RuntimeException("디자이너 정보가 없습니다."));
			user.setRole(Role.ROLE_DESIGNER);
			userRepository.save(user);
		}
		else request.setState(2);
		licenseRequestRepository.save(request);
	}
}
