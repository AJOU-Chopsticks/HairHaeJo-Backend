package Chopsticks.HairHaeJoBackend.service;

import Chopsticks.HairHaeJoBackend.dto.user.ChangePasswordRequestDto;
import Chopsticks.HairHaeJoBackend.entity.license.LicenseRequest;
import Chopsticks.HairHaeJoBackend.entity.license.LicenseRequestRepository;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import Chopsticks.HairHaeJoBackend.dto.user.AuthResponseDto;
import Chopsticks.HairHaeJoBackend.dto.user.SignupRequestDto;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import Chopsticks.HairHaeJoBackend.entity.user.UserRepository;
import Chopsticks.HairHaeJoBackend.jwt.TokenProvider;
import Chopsticks.HairHaeJoBackend.dto.user.LoginRequestDto;
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
    private final CustomUserDetailsService userDetailsService;

    // 회원가입 로직
    public void signup(MultipartFile image, SignupRequestDto requestDto) throws IOException {
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
    }

    // 로그인 로직
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
        return jwt;
    }

    // Auth 로직
    public AuthResponseDto auth() {
        User user = userRepository.findById(SecurityUtil.getCurrentMemberId())
            .orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다."));
        return AuthResponseDto.of(user);
    }

    // 계정 정보 변경 로직
    public void changeAccountInfo(MultipartFile image, SignupRequestDto requestDto)
        throws IOException {
        if (userRepository.existsByPhoneNumber(requestDto.getPhoneNumber())) {
            throw new RuntimeException("중복된 휴대폰 번호입니다.");
        }
        User user = userRepository.findById(SecurityUtil.getCurrentMemberId())
            .orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다."));
        user.setName(requestDto.getName());
        user.setPhoneNumber(requestDto.getPhoneNumber());
        user.setGender(requestDto.getGender());
        user.setAge(requestDto.getAge());
        if (image != null) {
            user.setProfileImage(s3UploadService.upload(image));
        }

        userRepository.save(user);
    }

    // 비밀번호 변경 로직
    public void changePassword(ChangePasswordRequestDto requestDto) {
        User user = userRepository.findById(SecurityUtil.getCurrentMemberId())
            .orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다."));
        if (!passwordEncoder.matches(requestDto.getExPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }
        user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
        userRepository.save(user);
    }

    // 헤어디자이너 등록 로직
    public void licenseRegister(MultipartFile image) throws IOException {
        LicenseRequest licenseRequest = LicenseRequest.builder()
            .designerId(SecurityUtil.getCurrentMemberId())
            .image(s3UploadService.upload(image))
            .build();
        licenseRequestRepository.save(licenseRequest);
    }

    // 회원탈퇴 로직
    public void withdrawal(LoginRequestDto requestDto) {
        User user = userRepository.findById(SecurityUtil.getCurrentMemberId())
            .orElseThrow(() -> new RuntimeException("로그인 정보가 없습니다."));
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다");
        }
        userRepository.deleteById(user.getId());
    }
}
