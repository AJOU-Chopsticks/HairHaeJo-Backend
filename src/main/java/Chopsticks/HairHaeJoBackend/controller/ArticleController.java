package Chopsticks.HairHaeJoBackend.controller;

import Chopsticks.HairHaeJoBackend.dto.APIMessages;
import Chopsticks.HairHaeJoBackend.dto.article.ChangeArticleDto;
import Chopsticks.HairHaeJoBackend.dto.article.ArticleIdDto;
import Chopsticks.HairHaeJoBackend.dto.article.MakeArticleDto;

import Chopsticks.HairHaeJoBackend.entity.Article;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import Chopsticks.HairHaeJoBackend.service.ArticleService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import java.util.Collection;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/advice")
public class ArticleController {

    private final ArticleService articleService;

    //게시글 작성
    @PostMapping(value="/article")
    public ResponseEntity<APIMessages> posting(@RequestPart(value = "beforeimage",required = false) MultipartFile beforeimage,@RequestPart(value = "afterimage",required = false) MultipartFile afterimage,@RequestParam("jsonlist") String jsonList) throws IOException

    {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        MakeArticleDto articleDto = objectMapper.readValue(jsonList, new TypeReference<>() {});

        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("게시글 작성 성공")
                .data(articleService.post(beforeimage,afterimage,articleDto))
                .build();


        return ResponseEntity.ok(apiMessages);
    }


    //게시글 변경
    @PutMapping(value="/article")
    public ResponseEntity<APIMessages> retouching(@RequestPart(value = "beforeimage",required = false) MultipartFile beforeimage,@RequestPart(value = "afterimage",required = false) MultipartFile afterimage,@RequestParam("jsonlist") String jsonList) throws IOException

    {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        ChangeArticleDto articleDto = objectMapper.readValue(jsonList, new TypeReference<>() {});
        articleService.retouch(beforeimage,afterimage,articleDto);
        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("게시글 수정 성공")
                .build();

        return ResponseEntity.ok(apiMessages);
    }
    //게시글 삭제
    @DeleteMapping("/article")
    public ResponseEntity<APIMessages> Deleting(@RequestParam("jsonlist") String jsonList) throws IOException

    {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        ArticleIdDto articleDto = objectMapper.readValue(jsonList, new TypeReference<>() {});
        articleService.delete(Integer.parseInt(articleDto.getArticleId()));
        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("게시글 삭제 성공")
                .build();

        return ResponseEntity.ok(apiMessages);
    }

    @GetMapping("/article/list")
    public ResponseEntity<APIMessages> loadlist(@RequestParam("region") String region, @RequestParam(value = "category",required = false) String category, Model model) throws IOException {

        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("게시글 조회 성공")
                .data(articleService.loadlist(region,category))
                .build();
        return ResponseEntity.ok(apiMessages);
    }


    //검색

    @GetMapping("/article/search")
    public ResponseEntity<APIMessages> Searching(@RequestParam("keyword") String keyword) throws IOException

    {

        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("게시글 검색 성공")
                .data(articleService.searchkeyword(keyword))
                .build();

        return ResponseEntity.ok(apiMessages);
    }


    //게시글 조회
    @GetMapping("/article")
    public ResponseEntity<APIMessages> viewing(@RequestParam("articleId") String articleId) throws IOException

    {

        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("게시글 조회 성공")
                .data(articleService.view(Integer.parseInt(articleId)))
                .build();
        return ResponseEntity.ok(apiMessages);
    }


}
