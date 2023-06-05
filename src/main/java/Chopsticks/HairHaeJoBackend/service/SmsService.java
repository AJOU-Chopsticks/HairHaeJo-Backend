package Chopsticks.HairHaeJoBackend.service;

import Chopsticks.HairHaeJoBackend.dto.sms.SmsMessageDto;
import Chopsticks.HairHaeJoBackend.dto.sms.SmsRequestDto;
import Chopsticks.HairHaeJoBackend.dto.sms.SmsResponseDto;
import Chopsticks.HairHaeJoBackend.entity.reservation.ReservationRepository;
import Chopsticks.HairHaeJoBackend.entity.user.User;
import Chopsticks.HairHaeJoBackend.entity.user.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@EnableScheduling
public class SmsService {

	private final UserRepository userRepository;
	private final ReservationRepository reservationRepository;

	@Value("${naver-cloud-sms.accessKey}")
	private String accessKey;

	@Value("${naver-cloud-sms.secretKey}")
	private String secretKey;

	@Value("${naver-cloud-sms.serviceId}")
	private String serviceId;

	@Value("${naver-cloud-sms.senderPhone}")
	private String phone;

	public String makeSignature(Long time)
		throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
		String space = " ";
		String newLine = "\n";
		String method = "POST";
		String url = "/sms/v2/services/" + this.serviceId + "/messages";
		String timestamp = time.toString();
		String accessKey = this.accessKey;
		String secretKey = this.secretKey;

		String message = new StringBuilder()
			.append(method)
			.append(space)
			.append(url)
			.append(newLine)
			.append(timestamp)
			.append(newLine)
			.append(accessKey)
			.toString();

		SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(signingKey);

		byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
		String encodeBase64String = Base64.encodeBase64String(rawHmac);

		return encodeBase64String;
	}

	public SmsResponseDto sendSms(SmsMessageDto messageDto)
		throws JsonProcessingException, RestClientException, URISyntaxException, InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
		Long time = System.currentTimeMillis();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("x-ncp-apigw-timestamp", time.toString());
		headers.set("x-ncp-iam-access-key", accessKey);
		headers.set("x-ncp-apigw-signature-v2", makeSignature(time));

		List<SmsMessageDto> messages = new ArrayList<>();
		messages.add(messageDto);

		SmsRequestDto request = SmsRequestDto.builder()
			.type("SMS")
			.contentType("COMM")
			.countryCode("82")
			.from(phone)
			.content(messageDto.getContent())
			.messages(messages)
			.build();

		ObjectMapper objectMapper = new ObjectMapper();
		String body = objectMapper.writeValueAsString(request);
		HttpEntity<String> httpBody = new HttpEntity<>(body, headers);

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		SmsResponseDto response = restTemplate.postForObject(
			new URI("https://sens.apigw.ntruss.com/sms/v2/services/" + serviceId + "/messages"),
			httpBody, SmsResponseDto.class);

		return response;
	}

	@Scheduled(cron = "0 0 2 * * *")
	public void sendRevisitSms()
		throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
		LocalDate date = LocalDate.from(LocalDateTime.now().plusHours(9)).minusDays(20);
		List<Long> clients = reservationRepository.getRevisitAlarmList(date.toString());
		String content;
		for (Long clientId : clients) {
			User user = userRepository.findById(clientId)
				.orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
			content = "[헤어해죠~] " + user.getName() + "님의 마지막 예약 이후 20일이 지났습니다."
				+ " 재방문으로 스타일 UP 해보세요~!";
			SmsResponseDto responseDto = sendSms(
				SmsMessageDto.builder().to(user.getPhoneNumber()).content(content).build());
			System.out.println(responseDto.toString());
		}
	}

}
