package Chopsticks.HairHaeJoBackend.article;

import Chopsticks.HairHaeJoBackend.dto.article.MakeArticleDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ArticleControllerTest {
    private MockMvc mockMvc;
    @Test
    @DisplayName("사진/글 미첨부 작성")
    void emptyarticle() throws Exception {
        ObjectMapper article=new ObjectMapper();
        String article1=article.writeValueAsString(MakeArticleDto.builder()
                .title("빈 게시글")
                .body("")
                .image("")
                .region("영통구")
                .category("컷-뒷머리")
                .build());
        ResultActions actions= mockMvc.perform(post("/advice/article").content(article1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        actions.andExpect(status().isOk());

    }
}
