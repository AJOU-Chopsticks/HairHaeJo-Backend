package Chopsticks.HairHaeJoBackend.service;


import Chopsticks.HairHaeJoBackend.dto.review.ReviewResponseDto;
import Chopsticks.HairHaeJoBackend.dto.user.ChangeClientProfileRequestDto;
import Chopsticks.HairHaeJoBackend.dto.user.ClientProfileRequestDto;
import Chopsticks.HairHaeJoBackend.dto.user.ClientProfileSearchResponseDto;
import Chopsticks.HairHaeJoBackend.dto.usermemo.UserMemoRequestDto;
import Chopsticks.HairHaeJoBackend.dto.usermemo.UserMemoResponseDto;
import Chopsticks.HairHaeJoBackend.entity.portfolio.Portfolio;
import Chopsticks.HairHaeJoBackend.entity.review.Review;
import Chopsticks.HairHaeJoBackend.entity.user.ClientProfile;
import Chopsticks.HairHaeJoBackend.entity.user.Role;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import Chopsticks.HairHaeJoBackend.entity.user.UserRepository;
import Chopsticks.HairHaeJoBackend.entity.user_memo.UserMemo;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import Chopsticks.HairHaeJoBackend.repository.ClientProfileRepository;
import Chopsticks.HairHaeJoBackend.repository.UserMemoRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
			 userMemoRepository.save(requestDto.toUserMemo(getCurrentUser(), clientId));
		} else {
			throw new RuntimeException("디자이너가 아니기때문에 메모 생성 불가능합니다.");
		}
	}

	//유저에 대한 메모 조회
	public List<UserMemoResponseDto> SearchUserMemo(long clientId){
		if(Role.ROLE_DESIGNER.equals((getCurrentUser().getRole()))) {
			List<UserMemo> userMemos = userMemoRepository.findByClientIdAndDesigner(clientId,getCurrentUser());
			List<UserMemoResponseDto> response = new ArrayList<>();
			for(UserMemo userMemo: userMemos)
			{
				response.add(toUserMemoResponseDto(userMemo));
			}
			return response;
		} else{throw new RuntimeException("디자이너가 아니기때문에 메모 조회가 불가능합니다.");}
	}
	//유저 메모 삭제
	public void delete (int memoId){
		if(Role.ROLE_DESIGNER.equals((getCurrentUser().getRole()))) {
			UserMemo userMemo = userMemoRepository.findById(memoId)
				.orElseThrow(() -> new RuntimeException("메모아이디가 없습니다."));
			if(userMemo.getDesigner()==getCurrentUser()) {
				userMemoRepository.delete(userMemo);
			}
			else{throw new RuntimeException("자신이 작성한 메모가 아닙니다.");}
		}
		else{throw new RuntimeException("디자이너가 아니기때문에 메모 삭제가 불가능합니다.");}
	}
	private User getCurrentUser() {
		User user = userRepository.findById(SecurityUtil.getCurrentMemberId())
			.orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다."));
		return user;
	}

	private UserMemoResponseDto toUserMemoResponseDto(UserMemo userMemo){

		UserMemoResponseDto  responseDto = UserMemoResponseDto .builder()
			.memoId(userMemo.getMemoId())
			.memo(userMemo.getMemo())
			.build();
		return responseDto;
	}

}
