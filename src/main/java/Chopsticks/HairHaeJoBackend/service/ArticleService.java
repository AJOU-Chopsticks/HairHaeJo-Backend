package Chopsticks.HairHaeJoBackend.service;

import Chopsticks.HairHaeJoBackend.dto.article.*;
import Chopsticks.HairHaeJoBackend.entity.Article;
import Chopsticks.HairHaeJoBackend.entity.ArticleRepository;

import Chopsticks.HairHaeJoBackend.entity.Articlestate;
import Chopsticks.HairHaeJoBackend.jwt.SecurityUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Collection;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final S3UploadService s3UploadService;

    public ArticleIdDto post(MultipartFile before, MultipartFile after, MakeArticleDto articleDto) throws IOException {
        Article nowarticle;
        String beforeurl,afterurl;
        long currentId=SecurityUtil.getCurrentMemberId();
        if (articleRepository.thereiswrote(currentId, Articlestate.WATING) != 0)
            throw new RuntimeException("이미 대기중인 작성글이 존재합니다");

        beforeurl=thereexistimage(before,null);
        afterurl=thereexistimage(after,null);

        nowarticle = articleRepository.save(articleDto.toArticle(currentId, beforeurl, afterurl));

        return new ArticleIdDto(Integer.toString(nowarticle.getId()));
    }

    public void retouch(MultipartFile before, MultipartFile after, ChangeArticleDto articleDto) throws IOException {
        Article article=articleRepository.findById(Integer.parseInt(articleDto.getArticleId()))
                .orElseThrow(() -> new RuntimeException("해당 게시글이 만료되었습니다"));
        if(article.getState() == Articlestate.FINISHED) {
            throw new RuntimeException("이미 완료된 게시글입니다");

        }
        String beforeurl=thereexistimage(before,article.getBeforeImage());
        String afterurl=thereexistimage(after,article.getAfterImage());
        article.retouching(
                        articleDto.getTitle(),
                        articleDto.getBody(),
                        articleDto.getRegion(),
                        articleDto.getCategory(),
                        articleDto.getGender(),
                        articleDto.getTag(),
                        beforeurl,
                        afterurl);
        articleRepository.save(article);
    }

    public void delete(int currentArticleId)  {
        articleRepository.deleteById(currentArticleId);

    }



    public Collection<ArticlelistResponseDto> loadlist(String region, String category,String gender,String tag) throws IOException {
        Collection<ArticlelistResponseDto> articleCollection=articleRepository.listfilter(region,category,gender,tag);
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        String temp=objectMapper.writeValueAsString(articleCollection);
        return articleCollection;
    }







     public Collection<ArticlelistResponseDto> searchkeyword(String keyword) throws IOException {
        if(keyword.isEmpty()) {
            throw new RuntimeException("키워드 입력후 검색해 주세요");
        }
        Collection<ArticlelistResponseDto> articleCollection=articleRepository.findkeyword(keyword);
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        String list=objectMapper.writeValueAsString(articleCollection);
        return articleCollection;
    }

    public ArticleViewDto view(int articleId) throws IOException {
        ArticleViewDto articleview =articleRepository.viewArticle(articleId);
        if(articleview==null)
            throw new RuntimeException("존재하지 않는 게시글입니다");

        return articleview;
    }



    private String thereexistimage(MultipartFile now,String last) throws IOException {
        if(now==null) {
            if(last==null) return null;
            else return last;

        }
        else {
            return s3UploadService.upload(now);
        }

    }

}