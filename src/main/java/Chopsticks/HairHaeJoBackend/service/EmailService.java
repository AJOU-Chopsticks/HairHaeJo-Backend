package Chopsticks.HairHaeJoBackend.service;

import Chopsticks.HairHaeJoBackend.dto.user.ResetPasswordRequestDto;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import Chopsticks.HairHaeJoBackend.entity.user.UserRepository;
import java.util.Random;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final UserRepository userRepository;
    private final JavaMailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    private MimeMessage createConfirmMessage(String to, String code) throws Exception {
        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(RecipientType.TO, to);//보내는 대상
        message.setSubject("[헤어해죠~] 회원가입을 위한 이메일 인증 코드입니다.");//제목

        String msgg = "";
        msgg += "<div style='margin:20px;'>";
        msgg += "<h1>안녕하세요. 헤어해죠~입니다. </h1>";
        msgg += "<br>";
        msgg += "<p>회원가입을 위해 아래 이메일 인증 코드를 복사해 입력해주세요. <p>";
        msgg += "<br>";
        msgg += "<p>감사합니다. <p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>이메일 인증 코드</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "CODE : <strong>";
        msgg += code + "</strong><div><br/> ";
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("hairhaejo@gmail.com", "해어해죠~"));

        return message;
    }

    private MimeMessage createPasswordMessage(String to, String code) throws Exception {
        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(RecipientType.TO, to);//보내는 대상
        message.setSubject("[헤어해죠~] 비밀번호 초기화 안내");//제목

        String msgg = "";
        msgg += "<div style='margin:20px;'>";
        msgg += "<h1>안녕하세요. 헤어해죠~입니다. </h1>";
        msgg += "<br>";
        msgg += "<p>로그인을 위해 아래 초기화된 새 비밀번호를 이용해주세요. <p>";
        msgg += "<br>";
        msgg += "<p>감사합니다. <p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>새 비밀번호</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "PASSWORD : <strong>";
        msgg += code + "</strong><div><br/> ";
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("hairhaejo@gmail.com", "해어해죠~"));

        return message;
    }

    private MimeMessage createDesignerConfirmMessage(String to) throws Exception {
        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(RecipientType.TO, to);//보내는 대상
        message.setSubject("[헤어해죠~] 디자이너 가입이 승인되었습니다.");//제목

        String msgg = "";
        msgg += "<div style='margin:20px;'>";
        msgg += "<h1>안녕하세요. 헤어해죠~입니다. </h1>";
        msgg += "<br>";
        msgg += "<p>요청해주신 디자이너 가입이 승인되었습니다. 이제 디자이너 기능들을 사용하실 수 있습니다. <p>";
        msgg += "<br>";
        msgg += "<p>감사합니다. <p>";
        msgg += "<br>";
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("hairhaejo@gmail.com", "해어해죠~"));

        return message;
    }

    private MimeMessage createDesignerDeniedMessage(String to) throws Exception {
        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(RecipientType.TO, to);//보내는 대상
        message.setSubject("[헤어해죠~] 디자이너 가입 거절 알림");//제목

        String msgg = "";
        msgg += "<div style='margin:20px;'>";
        msgg += "<h1>안녕하세요. 헤어해죠~입니다. </h1>";
        msgg += "<br>";
        msgg += "<p>귀하의 디자이너 가입 요청이 조건을 충족하지 못하여 거절되었습니다. <p>";
        msgg += "<br>";
        msgg += "<p>이용을 위해 검토 후 다시 요청해주세요. <p>";
        msgg += "<br>";
        msgg += "<p>감사합니다. <p>";
        msgg += "<br>";
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("hairhaejo@gmail.com", "해어해죠~"));

        return message;
    }

    private String createCode() {
        StringBuffer code = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) {
            int index = rnd.nextInt(3);
            switch (index) {
                case 0:
                    code.append((char) ((int) (rnd.nextInt(26)) + 97));
                    break;
                case 1:
                    code.append((char) ((int) (rnd.nextInt(26)) + 65));
                    break;
                case 2:
                    code.append((rnd.nextInt(10)));
                    break;
            }
        }
        return code.toString();
    }

    public String sendConfirmMessage(String to) throws Exception {
        String code = createCode();
        MimeMessage message = createConfirmMessage(to, code);
        try {
            emailSender.send(message);
        } catch (MailException e) {
            throw new RuntimeException("메일 전송에 실패했습니다.");
        }
        return code;
    }

    public void sendDesignerConfirmMessage(String to) throws Exception {
        MimeMessage message = createDesignerConfirmMessage(to);
        try {
            emailSender.send(message);
        } catch (MailException e) {
            throw new RuntimeException("메일 전송에 실패했습니다.");
        }
    }

    public void sendDesignerDeniedMessage(String to) throws Exception {
        MimeMessage message = createDesignerDeniedMessage(to);
        try {
            emailSender.send(message);
        } catch (MailException e) {
            throw new RuntimeException("메일 전송에 실패했습니다.");
        }
    }

    @Transactional
    public void resetPassword(ResetPasswordRequestDto requestDto) throws Exception {
        User user = userRepository.findByEmail(requestDto.getEmail())
            .orElseThrow(() -> new RuntimeException("일치하는 사용자가 없습니다."));
        if (!user.getName().equals(requestDto.getName())) {
            throw new RuntimeException("일치하는 사용자가 없습니다.");
        }

        String pw = createCode();
        user.setPassword(passwordEncoder.encode(pw));
        userRepository.save(user);

        MimeMessage message = createPasswordMessage(user.getEmail(), pw);
        try {
            emailSender.send(message);
        } catch (MailException e) {
            throw new RuntimeException("메일 전송에 실패했습니다.");
        }
    }
}
