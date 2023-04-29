package Chopsticks.HairHaeJoBackend.service;

import Chopsticks.HairHaeJoBackend.dto.article.ChangeArticleDto;
import Chopsticks.HairHaeJoBackend.dto.article.DeleteArticleDto;
import Chopsticks.HairHaeJoBackend.dto.article.MakeArticleDto;
import Chopsticks.HairHaeJoBackend.entity.Article;
import Chopsticks.HairHaeJoBackend.entity.ArticleRepository;

import Chopsticks.HairHaeJoBackend.entity.Articlestate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final S3UploadService s3UploadService;

    public String post(MultipartFile before, MultipartFile after, MakeArticleDto articleDto, Long currentMemberId) throws IOException {
        Article nowarticle;
        String beforeurl,afterurl;
        if(articleRepository.thereiswrote(currentMemberId,Articlestate.WATING)!=0)
            throw new RuntimeException("이미 대기중인 작성글이 존재합니다");

        if (before==null) beforeurl=null;
        else beforeurl=s3UploadService.upload(before);

        if(after==null) afterurl=null;
        else afterurl= s3UploadService.upload(after);

        nowarticle = articleRepository.save(articleDto.toArticle(currentMemberId, beforeurl, afterurl));
        DeleteArticleDto returndata=new DeleteArticleDto(Integer.toString(nowarticle.getId()));
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        String articlenumber=objectMapper.writeValueAsString(returndata);
        return articlenumber;
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
                        beforeurl,
                        afterurl);
        articleRepository.save(article);
    }

    public void delete(int currentArticleId)  {
        articleRepository.deleteById(currentArticleId);

    }


   /*
    public Collection<Article> loadlist(String region, String category) throws IOException {
        Collection<Article> articleCollection=null;
        if (category.isEmpty()) {
            articleCollection = articleRepository.findByabstractionLocation(region);
        }
        else {
            List<String> categorylist = new ArrayList<String>();
            String[] splitStr = category.split("-");
            for(int i=0; i<splitStr.length; i++){
                categorylist.add("-"+splitStr[i]+"-");
            }
            articleRepository.findBycategoryContaining(categorylist);

        }
        return articleCollection;
    }



     public String searchkeyword(String keyword) throws IOException {
        if(keyword.isEmpty()) {
            throw new RuntimeException("키워드 입력후 검색해 주세요");
        }
        Collection<Article> articleCollection=articleRepository.findIdAndwriterIdAndtitleBytitleContaining(keyword);
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        String list=objectMapper.writeValueAsString(articleCollection);
        return list;
    }

    public String view(int articleId) throws IOException {
        Article article=articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("해당 게시글이 만료되었습니다"));
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        String articlelist=objectMapper.writeValueAsString(article);
        return articlelist;
    }


    */
    private String thereexistimage(MultipartFile now,String last) throws IOException {
        if(now!=null) {
            return s3UploadService.upload(now);
        }
        else {
            if(last==null) return null;
            else return last;
        }

    }

}