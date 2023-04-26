package Chopsticks.HairHaeJoBackend.controller;

import Chopsticks.HairHaeJoBackend.dto.APIMessages;
import Chopsticks.HairHaeJoBackend.dto.article.DeleteArticleDto;
import Chopsticks.HairHaeJoBackend.dto.article.MakeArticleDto;

import Chopsticks.HairHaeJoBackend.entity.Article;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import Chopsticks.HairHaeJoBackend.service.ArticleService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import java.util.Collection;

import com.fasterxml.jackson.databind.util.JSONPObject;
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
    @PostMapping("/article")
    public ResponseEntity<APIMessages> posting(@RequestPart("beforeimage") MultipartFile beforeimage,@RequestPart("afterimage") MultipartFile afterimage,@RequestParam("jsonList") String jsonList) throws IOException

    {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        MakeArticleDto articleDto = objectMapper.readValue(jsonList, new TypeReference<>() {});
        int articleid=articleService.post(beforeimage,afterimage,articleDto,SecurityUtil.getCurrentMemberId());
        APIMessages apiMessages;
        apiMessages=APIMessages.builder().success(true)
                .message("게시글 작성 성공")
                .data(Integer.toString(articleid))
                .build();
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
        articleService.delete(Integer.parseInt(articleDto.getArticleId()));
        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("게시글 삭제 성공")
                .build();

        return ResponseEntity.ok(apiMessages);
    }
    /* 카테고리 검색 기능 오류로 임시 주석처리
    @GetMapping("/article/list")
    public void loadinglist(@RequestParam String region, @RequestParam String category, Model model) throws IOException {
        model.addAttribute("region",region);
        model.addAttribute("category",category);
        Collection<Article> articlelist=articleService.loadlist(region,category);
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        String list=objectMapper.writeValueAsString(articlelist);
        APIMessages apiMessages=APIMessages.builder().success(true)
                .message("게시글 삭제 성공")
                .data(list)
                .build();
    }

     */
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
