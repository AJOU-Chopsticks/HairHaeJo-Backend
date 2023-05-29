package Chopsticks.HairHaeJoBackend.service;


import Chopsticks.HairHaeJoBackend.dto.user.ChangeClientProfileRequestDto;
import Chopsticks.HairHaeJoBackend.dto.user.ClientProfileRequestDto;
import Chopsticks.HairHaeJoBackend.dto.user.ClientProfileSearchResponseDto;
import Chopsticks.HairHaeJoBackend.dto.usermemo.UserMemoRequestDto;
import Chopsticks.HairHaeJoBackend.dto.usermemo.UserMemoResponseDto;
import Chopsticks.HairHaeJoBackend.entity.user.ClientProfile;
import Chopsticks.HairHaeJoBackend.entity.user.Role;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import Chopsticks.HairHaeJoBackend.entity.user.UserRepository;
import Chopsticks.HairHaeJoBackend.entity.user_memo.UserMemo;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import Chopsticks.HairHaeJoBackend.repository.ClientProfileRepository;
import Chopsticks.HairHaeJoBackend.repository.UserMemoRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserMemoService {

	private final UserMemoRepository userMemoRepository;
	private final UserRepository userRepository;


	//유저에 관한 메모 생성
	public void addUserMemo (UserMemoRequestDto requestDto,long clientId) {

		if (Role.ROLE_DESIGNER.equals((getCurrentUser().getRole()))) {
			if (userMemoRepository.existsByClientId(clientId)&&
				userMemoRepository.existsByDesigner(getCurrentUser())) {
				throw new RuntimeException("이미 유저에대한 메모가 존재합니다.");
			} else{userMemoRepository.save(requestDto.toUserMemo(getCurrentUser(), clientId));}
		} else {
			throw new RuntimeException("디자이너가 아니기때문에 메모 생성 불가능합니다.");
		}
	}

	//유저에 대한 메모 조회
	public UserMemoResponseDto SearchUserMemo(long clientId){
		if(Role.ROLE_DESIGNER.equals((getCurrentUser().getRole()))) {
			UserMemo userMemo = userMemoRepository.findByClientIdAndDesigner(clientId,getCurrentUser());
			return UserMemoResponseDto.of(userMemo);
		} else{throw new RuntimeException("디자이너가 아니기때문에 메모 조회가 불가능합니다.");}
	}
	//유저 메모 수정
	public void changeUserMemo(UserMemoRequestDto requestDto, long clientId){
		if(Role.ROLE_DESIGNER.equals((getCurrentUser().getRole()))) {
			UserMemo userMemo = userMemoRepository.findByClientIdAndDesigner(clientId,
				getCurrentUser());
			userMemo.setMemo(requestDto.getMemo());
			userMemo.setUpdatedAt(LocalDateTime.now());
			userMemoRepository.save(userMemo);
		}
		else{throw new RuntimeException("디자이너가 아니기때문에 메모 수정이 불가능합니다.");}
	}


	private User getCurrentUser() {
		User user = userRepository.findById(SecurityUtil.getCurrentMemberId())
			.orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다."));
		return user;
	}

}
