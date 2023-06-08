package Chopsticks.HairHaeJoBackend.service;

import Chopsticks.HairHaeJoBackend.dto.report.ReportRequestDto;
import Chopsticks.HairHaeJoBackend.dto.user.ChangePasswordRequestDto;
import Chopsticks.HairHaeJoBackend.entity.designer.DesignerProfile;
import Chopsticks.HairHaeJoBackend.entity.license.LicenseRequest;
import Chopsticks.HairHaeJoBackend.entity.license.LicenseRequestRepository;
import Chopsticks.HairHaeJoBackend.entity.report.Report;
import Chopsticks.HairHaeJoBackend.entity.report.ReportRepository;
import Chopsticks.HairHaeJoBackend.entity.user.ClientProfile;
import Chopsticks.HairHaeJoBackend.entity.user.Role;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import Chopsticks.HairHaeJoBackend.dto.user.AuthResponseDto;
import Chopsticks.HairHaeJoBackend.dto.user.SignupRequestDto;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import Chopsticks.HairHaeJoBackend.entity.user.UserRepository;
import Chopsticks.HairHaeJoBackend.jwt.TokenProvider;
import Chopsticks.HairHaeJoBackend.dto.user.LoginRequestDto;
import Chopsticks.HairHaeJoBackend.entity.user.ClientProfileRepository;
import Chopsticks.HairHaeJoBackend.entity.designer.DesignerProfileRepository;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final LicenseRequestRepository licenseRequestRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder managerBuilder;
    private final S3UploadService s3UploadService;
    private final ReportRepository reportRepository;
    private final ClientProfileRepository clientProfileRepository;
    private final DesignerProfileRepository designerProfileRepository;

    // 회원가입
    public String signup(MultipartFile image, SignupRequestDto requestDto) throws IOException {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RuntimeException("중복된 이메일입니다.");
        }
        if (userRepository.existsByPhoneNumber(requestDto.getPhoneNumber())) {
            throw new RuntimeException("중복된 휴대폰 번호입니다.");
        }
        if (image == null) {
            userRepository.save(requestDto.toUser(null, passwordEncoder));
        } else {
            userRepository.save(requestDto.toUser(s3UploadService.upload(image), passwordEncoder));
        }

        UsernamePasswordAuthenticationToken token = LoginRequestDto.builder()
            .email(requestDto.getEmail()).password(requestDto.getPassword()).build()
            .toAuthentication();
        Authentication authentication = managerBuilder.getObject().authenticate(token);
        String jwt = tokenProvider.generateToken(authentication);

        return jwt;
    }

    // 로그인
    public String login(LoginRequestDto requestDto, HttpServletResponse response) {

        UsernamePasswordAuthenticationToken token = requestDto.toAuthentication();
        Authentication authentication = managerBuilder.getObject().authenticate(token);

        String jwt = tokenProvider.generateToken(authentication);
/*
        Cookie cookie = new Cookie("jwt", jwt);
        cookie.setMaxAge(60 * 60 * 24); // 쿠키 유효 기간 설정
        //cookie.setHttpOnly(true); // 자바스크립트에서 쿠키에 접근하지 못하도록 설정
        cookie.setPath("/"); // 모든 경로에서 쿠키 접근 가능하도록 설정
        response.addCookie(cookie);
*/
        User user = userRepository.findByEmail(requestDto.getEmail())
            .orElseThrow(() -> new RuntimeException("계정 정보가 없습니다."));
        user.setFcmToken(requestDto.getFcmToken());
        userRepository.save(user);

        if(user.isSuspended()) throw new RuntimeException("정지된 사용자입니다.");

        return jwt;
    }

    // Auth
    public AuthResponseDto auth() {
        User user = getCurrentUser();
        String location;
        AuthResponseDto responseDto = AuthResponseDto.of(user);
        if(user.getRole() == Role.ROLE_USER || user.getRole() == Role.ROLE_ADMIN){
            ClientProfile profile = clientProfileRepository.findByUser(user);
            location = profile.getAbstractLocation();
        } else {
            DesignerProfile profile = designerProfileRepository.findByUser(user);
            location = profile.getHairSalonAddress();
        }
        responseDto.setLocation(location);
        return responseDto;
    }

    // Info
    public AuthResponseDto getInfo(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
        return AuthResponseDto.of(user);
    }

    // 계정 정보 변경
    public void changeAccountInfo(MultipartFile image, SignupRequestDto requestDto)
        throws IOException {
        User user = getCurrentUser();
        if (!requestDto.getPhoneNumber().equals(user.getPhoneNumber())){
            if (userRepository.existsByPhoneNumber(requestDto.getPhoneNumber())) {
                throw new RuntimeException("중복된 휴대폰 번호입니다.");
            }
            user.setPhoneNumber(requestDto.getPhoneNumber());
        }
        user.setName(requestDto.getName());
        user.setGender(requestDto.getGender());
        user.setAge(requestDto.getAge());
        if (image != null) {
            user.setProfileImage(s3UploadService.upload(image));
        }

        userRepository.save(user);
    }

    // 비밀번호 변경
    public void changePassword(ChangePasswordRequestDto requestDto) {
        User user = getCurrentUser();
        if (!passwordEncoder.matches(requestDto.getExPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }
        user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
        userRepository.save(user);
    }

    // 헤어디자이너 등록
    public void licenseRegister(MultipartFile image) throws IOException {
        LicenseRequest licenseRequest = LicenseRequest.builder()
            .designerId(getCurrentUser())
            .image(s3UploadService.upload(image))
            .build();
        licenseRequestRepository.save(licenseRequest);
    }

    // 로그아웃 (FCM token 초기화)
    public void logout(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("유저 정보가 없습니다."));
        user.setFcmToken(null);
        userRepository.save(user);
    }

    // 회원탈퇴
    public void withdrawal(LoginRequestDto requestDto) {
        User user = getCurrentUser();
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }
        userRepository.deleteById(user.getId());
    }

    //사용자 신고
    public void report(ReportRequestDto requestDto){
        User target = userRepository.findById(requestDto.getTargetUserId())
            .orElseThrow(() -> new RuntimeException("상대방 정보가 없습니다."));
        Report report = Report.builder()
            .reportType(requestDto.getReportType())
            .reporterId(getCurrentUser())
            .targetId(target)
            .reportReason(requestDto.getReason())
            .objectId(requestDto.getTargetId())
            .build();
        reportRepository.save(report);
    }
    public String getUserFcmToken(long designerId) {
        User designer=userRepository.findById(designerId) .orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다."));
        return designer.getFcmToken();
    }
    private User getCurrentUser() {
        User user = userRepository.findById(SecurityUtil.getCurrentMemberId())
            .orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다."));
        return user;
    }


}
