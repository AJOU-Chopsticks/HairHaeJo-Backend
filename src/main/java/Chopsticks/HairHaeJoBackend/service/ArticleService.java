package Chopsticks.HairHaeJoBackend.service;

import Chopsticks.HairHaeJoBackend.dto.article.MakeArticleDto;
import Chopsticks.HairHaeJoBackend.entity.Article;
import Chopsticks.HairHaeJoBackend.entity.ArticleRepository;

import Chopsticks.HairHaeJoBackend.entity.Articlestate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final S3UploadService s3UploadService;
    public int post(MultipartFile before, MultipartFile after, MakeArticleDto articleDto, Long currentMemberId) throws IOException {
        Article nowarticle=null;
        if (before.isEmpty()){
            if(after.isEmpty()) {
                nowarticle=articleRepository.save(articleDto.toArticle(currentMemberId,null,null));
            }
            else nowarticle=articleRepository.save(articleDto.toArticle(currentMemberId,null,s3UploadService.upload(after)));
        }
        else {
            if(after.isEmpty()){
                nowarticle=articleRepository.save(articleDto.toArticle(currentMemberId,s3UploadService.upload(before),null));
            }
            else nowarticle=articleRepository.save(articleDto.toArticle(currentMemberId,s3UploadService.upload(before),s3UploadService.upload(after)));
        }
        if(nowarticle!=null) {
            return nowarticle.getId();
        }
        else {
            return -1;
        }

    }
    public void retouch(MultipartFile before, MultipartFile after, MakeArticleDto articleDto, Long currentMemberId) throws IOException {
        Article nowarticle=null;
        articleRepository.findbywriterAndstate(currentMemberId, Articlestate.WATING);

        if (before.isEmpty()){
            if(after.isEmpty()) {
                articleRepository.changeArticle(articleDto.getTitle(), articleDto.getBody(),null,null,articleDto.getRegion(), articleDto.getCategory(), currentMemberId,Articlestate.WATING);
            }
            else articleRepository.changeArticle(articleDto.getTitle(), articleDto.getBody(),null,s3UploadService.upload(after),articleDto.getRegion(), articleDto.getCategory(), currentMemberId,Articlestate.WATING);
        }
        else {
            if(after.isEmpty()){
                articleRepository.changeArticle(articleDto.getTitle(), articleDto.getBody(),s3UploadService.upload(before),null,articleDto.getRegion(), articleDto.getCategory(), currentMemberId,Articlestate.WATING);
            }
            else articleRepository.changeArticle(articleDto.getTitle(), articleDto.getBody(),s3UploadService.upload(before),s3UploadService.upload(after),articleDto.getRegion(), articleDto.getCategory(), currentMemberId,Articlestate.WATING);
        }
    }
    public void delete(int currentArticleId) throws IOException {
        articleRepository.deleteById(Integer.toUnsignedLong(currentArticleId));

    }
}
