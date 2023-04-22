package Chopsticks.HairHaeJoBackend.controller;

import Chopsticks.HairHaeJoBackend.dto.APIMessages;
import Chopsticks.HairHaeJoBackend.dto.article.MakeArticleDto;
import Chopsticks.HairHaeJoBackend.dto.user.LoginRequestDto;
import Chopsticks.HairHaeJoBackend.dto.user.SignupRequestDto;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import Chopsticks.HairHaeJoBackend.service.ArticleService;
import Chopsticks.HairHaeJoBackend.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/advice/article")
public class ArticleController {

    private final ArticleService articleService;

    //게시글 작성
    @PostMapping("/")
    public ResponseEntity<APIMessages> posting(@RequestPart("beforeimage") MultipartFile beforeimage,@RequestPart("afterimage") MultipartFile afterimage,@RequestParam("jsonList") String jsonList) throws IOException

    {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        MakeArticleDto articleDto = objectMapper.readValue(jsonList, new TypeReference<>() {});
        articleService.post(beforeimage,afterimage,articleDto,SecurityUtil.getCurrentMemberId());
        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("게시글 작성 성공")
                .build();
        return ResponseEntity.ok(apiMessages);
    }


    //로그인

}
