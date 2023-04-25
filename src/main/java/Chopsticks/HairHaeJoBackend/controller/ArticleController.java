package Chopsticks.HairHaeJoBackend.controller;

import Chopsticks.HairHaeJoBackend.dto.APIMessages;
import Chopsticks.HairHaeJoBackend.dto.article.DeleteArticleDto;
import Chopsticks.HairHaeJoBackend.dto.article.MakeArticleDto;

import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import Chopsticks.HairHaeJoBackend.service.ArticleService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/advice")
public class ArticleController {

    private final ArticleService articleService;

    //게시글 작성
    @PostMapping("/article")
    public ResponseEntity<APIMessages> posting(@RequestPart("beforeimage") MultipartFile beforeimage,@RequestPart("afterimage") MultipartFile afterimage,@RequestParam("jsonList") String jsonList) throws IOException

    {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        MakeArticleDto articleDto = objectMapper.readValue(jsonList, new TypeReference<>() {});
        int articleid=articleService.post(beforeimage,afterimage,articleDto,SecurityUtil.getCurrentMemberId());
        APIMessages apiMessages;
        if(articleid==-1) {
            apiMessages=APIMessages.builder().success(true)
                    .message("게시글 작성 실패")
                    .build();
        }
        else {
            apiMessages=APIMessages.builder().success(true)
                    .message("ArticleId:"+Integer.toString(articleid))
                    .build();
        }

        return ResponseEntity.ok(apiMessages);
    }


    //게시글 변경
    @PutMapping("/article")
    public ResponseEntity<APIMessages> retouching(@RequestPart("beforeimage") MultipartFile beforeimage,@RequestPart("afterimage") MultipartFile afterimage,@RequestParam("jsonList") String jsonList) throws IOException

    {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        MakeArticleDto articleDto = objectMapper.readValue(jsonList, new TypeReference<>() {});
        articleService.retouch(beforeimage,afterimage,articleDto,SecurityUtil.getCurrentMemberId());
        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("게시글 수정 성공")
                .build();

        return ResponseEntity.ok(apiMessages);
    }
    //게시글 삭제
    @DeleteMapping("/article")
    public ResponseEntity<APIMessages> Deleting(@RequestParam("jsonList") String jsonList) throws IOException

    {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        DeleteArticleDto articleDto = objectMapper.readValue(jsonList, new TypeReference<>() {});
        articleService.delete(articleDto.getArticleId());
        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("게시글 수정 성공")
                .build();

        return ResponseEntity.ok(apiMessages);
    }

}
