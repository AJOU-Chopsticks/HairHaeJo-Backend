package Chopsticks.HairHaeJoBackend.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import Chopsticks.HairHaeJoBackend.dto.user.SignupRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("회원가입 성공")
	void signup() throws Exception {
		//given
		ObjectMapper objectMapper = new ObjectMapper();
		String object = objectMapper.writeValueAsString(SignupRequestDto.builder()
			.email("user@example.com")
			.password("password")
			.name("user")
			.phoneNumber("01012345678")
			.gender(1)
			.age(20)
			.build());

		//when
		ResultActions actions = mockMvc.perform(post("/user/signup")
			.content(object)
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON));

		//then
		actions.andExpect(status().isOk());
	}

	@Test
	@DisplayName("회원가입 실패: 중복된 이메일")
	void signup_duplicated_email() throws Exception {
		//given
		ObjectMapper user1 = new ObjectMapper();
		String object1 = user1.writeValueAsString(SignupRequestDto.builder()
			.email("user1@example.com")
			.password("password")
			.name("user1")
			.phoneNumber("01011111111")
			.gender(1)
			.age(20)
			.build());

		ObjectMapper user2 = new ObjectMapper();
		String object2 = user2.writeValueAsString(SignupRequestDto.builder()
			.email("user1@example.com")
			.password("password")
			.name("user2")
			.phoneNumber("01022222222")
			.gender(1)
			.age(20)
			.build());

		//when
		mockMvc.perform(post("/user/signup")
			.content(object1)
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON));

		ResultActions actions = mockMvc.perform(post("/user/signup")
			.content(object2)
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON));

		//then
		actions.andExpect(status().is4xxClientError());
	}

	@Test
	@DisplayName("회원가입 실패: 중복된 전화번호")
	void signup_duplicated_phoneNumber() throws Exception {
		//given
		ObjectMapper user1 = new ObjectMapper();
		String object1 = user1.writeValueAsString(SignupRequestDto.builder()
			.email("user1@example.com")
			.password("password")
			.name("user1")
			.phoneNumber("01011111111")
			.gender(1)
			.age(20)
			.build());

		ObjectMapper user2 = new ObjectMapper();
		String object2 = user2.writeValueAsString(SignupRequestDto.builder()
			.email("user2@example.com")
			.password("password")
			.name("user2")
			.phoneNumber("01011111111")
			.gender(1)
			.age(20)
			.build());

		//when
		mockMvc.perform(post("/user/signup")
			.content(object1)
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON));

		ResultActions actions = mockMvc.perform(post("/user/signup")
			.content(object2)
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_JSON));

		//then
		actions.andExpect(status().is4xxClientError());
	}
}