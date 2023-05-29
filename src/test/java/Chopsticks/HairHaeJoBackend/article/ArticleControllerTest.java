package Chopsticks.HairHaeJoBackend.article;

import Chopsticks.HairHaeJoBackend.dto.article.MakeArticleDto;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ArticleControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("사진/글 미첨부 작성")
    void emptyarticle() throws Exception {
        ObjectMapper article=new ObjectMapper();

        String article1=article.writeValueAsString(MakeArticleDto.builder()
                .title("실험글 제목")
                .body("실험글 내용")
                .region("수원시")
                .category("1-2-3-4")
                .build());
        Cookie cookie = new Cookie("jwt", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI5MyIsImF1dGgiOiJST0xFX1VTRVIiLCJleHAiOjE2ODI1NTczNzl9.IohEhUn_G9-LAMUnXWuq0v71H83KkmmFKAgdxdIkcv3O76lH7OQvghcxULuA3S0wwxZtDLfC7gCy-VBOtoD90A");
        ResultActions actions= mockMvc

                .perform(post("/advice/article")
                        .header("AUTHORIZATION","eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI5MyIsImF1dGgiOiJST0xFX1VTRVIiLCJleHAiOjE2ODI1OTU1ODV9.pVLrziY_kiE912fiL6ckWoo3r-nPf-uAktBCqiuIxjCgA24O6YFDzbqHH7H43Zp_QzLdTLAzbYi02nNfmz9GEQ")
                        .cookie(cookie)
                        .content(article1)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON));

        actions.andExpect(status().isOk());

    }
}
