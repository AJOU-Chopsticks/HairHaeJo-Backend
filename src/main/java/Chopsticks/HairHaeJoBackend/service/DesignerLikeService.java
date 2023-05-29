package Chopsticks.HairHaeJoBackend.service;

import Chopsticks.HairHaeJoBackend.dto.designer.RecommendResponseDto;
import Chopsticks.HairHaeJoBackend.entity.designer.DesignerLike;
import Chopsticks.HairHaeJoBackend.entity.designer.DesignerLikeRepository;
import Chopsticks.HairHaeJoBackend.entity.designer.DesignerProfile;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import Chopsticks.HairHaeJoBackend.entity.user.UserRepository;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import Chopsticks.HairHaeJoBackend.repository.DesignerProfileRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DesignerLikeService {

	private final DesignerLikeRepository designerLikeRepository;
	private final UserRepository userRepository;
	private final DesignerProfileRepository designerProfileRepository;

	public void addDesignerLike(Long designerId){
		User client = getCurrentUser();
		User designer = userRepository.findById(designerId)
			.orElseThrow(() -> new RuntimeException("디자이너 정보가 없습니다."));
		if(designerLikeRepository.existsByClientIdAndDesignerId(client, designer)){
			throw new RuntimeException("이미 관심 등록된 디자이너입니다.");
		} else {
			designerLikeRepository.save(DesignerLike.builder()
				.clientId(client).designerId(designer)
				.build());
		}
	}

	public void cancelDesignerLike(Long designerId){
		User client = getCurrentUser();
		User designer = userRepository.findById(designerId)
			.orElseThrow(() -> new RuntimeException("디자이너 정보가 없습니다."));
		designerLikeRepository.deleteByClientIdAndDesignerId(client, designer);
	}

	public List<RecommendResponseDto> getDesignerLike(){
		List<DesignerLike> designerList = designerLikeRepository.findByClientId(getCurrentUser());
		List<RecommendResponseDto> list = new ArrayList<>();

		for (DesignerLike designerLike : designerList) {
			DesignerProfile designerProfile = designerProfileRepository.findByUser(designerLike.getDesignerId());
			RecommendResponseDto responseDto = RecommendResponseDto.builder()
				.designerId(designerLike.getDesignerId().getId())
				.Name(designerLike.getDesignerId().getName())
				.hairSalonAddress(designerProfile.getHairSalonAddress())
				.hairSalonName(designerProfile.getHairSalonName())
				.ProfileImage(designerLike.getDesignerId().getProfileImage()).build();
			list.add(responseDto);
		}
		return list;
	}


	private User getCurrentUser() {
		User user = userRepository.findById(SecurityUtil.getCurrentMemberId())
			.orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다."));
		return user;
	}
}
