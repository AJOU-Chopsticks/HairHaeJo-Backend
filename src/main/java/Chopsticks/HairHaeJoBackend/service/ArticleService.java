package Chopsticks.HairHaeJoBackend.service;

import Chopsticks.HairHaeJoBackend.dto.article.ChangeArticleDto;
import Chopsticks.HairHaeJoBackend.dto.article.MakeArticleDto;
import Chopsticks.HairHaeJoBackend.entity.Article;
import Chopsticks.HairHaeJoBackend.entity.ArticleRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Collection;


@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final S3UploadService s3UploadService;

    public String post(MultipartFile before, MultipartFile after, MakeArticleDto articleDto, Long currentMemberId) throws IOException {
        Article nowarticle;
        if (before.isEmpty()) {
            if (after.isEmpty()) {
                nowarticle = articleRepository.save(articleDto.toArticle(currentMemberId, null, null));
            } else
                nowarticle = articleRepository.save(articleDto.toArticle(currentMemberId, null, s3UploadService.upload(after)));
        } else {
            if (after.isEmpty()) {
                nowarticle = articleRepository.save(articleDto.toArticle(currentMemberId, s3UploadService.upload(before), null));
            } else
                nowarticle = articleRepository.save(articleDto.toArticle(currentMemberId, s3UploadService.upload(before), s3UploadService.upload(after)));
        }
        return Integer.toString(nowarticle.getId());
    }

    public void retouch(MultipartFile before, MultipartFile after, ChangeArticleDto articleDto) throws IOException {
        Article article=articleRepository.findById(Long.parseLong(articleDto.getArticleId()))
                .orElseThrow(() -> new RuntimeException("해당 게시글이 만료되었습니다"));
        String beforeurl,afterurl;
        boolean notbefore=false,notafter=false;
        if (before==null)  {
            beforeurl=null;
            notbefore=true;
        }
        else beforeurl=s3UploadService.upload(before);

        if(after==null)  {
            afterurl=null;
            notafter=true;
        }
        else afterurl= s3UploadService.upload(after);

        if(notafter&&article.getAfterImage() != null) afterurl = article.getAfterImage();

        if(notbefore&&article.getBeforeImage() != null)  beforeurl = article.getAfterImage();



        articleRepository.changeArticle(articleDto.getTitle(), articleDto.getBody(), beforeurl,afterurl, articleDto.getRegion(), articleDto.getCategory(), Integer.parseInt(articleDto.getArticleId())) ;
    }

    public void delete(int currentArticleId)  {
        articleRepository.deleteById(Integer.toUnsignedLong(currentArticleId));

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
                categorylist.add(splitStr[i]);
            }
            articleRepository.findBycategoryContaining(categorylist);

        }
        return articleCollection;
    }

    */
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
        Article article=articleRepository.findById(Integer.toUnsignedLong(articleId))
                .orElseThrow(() -> new RuntimeException("해당 게시글이 만료되었습니다"));
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule());
        String articlelist=objectMapper.writeValueAsString(article);
        return articlelist;
    }


}