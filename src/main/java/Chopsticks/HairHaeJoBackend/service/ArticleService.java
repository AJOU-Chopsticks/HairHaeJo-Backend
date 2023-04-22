package Chopsticks.HairHaeJoBackend.service;

import Chopsticks.HairHaeJoBackend.dto.article.MakeArticleDto;
import Chopsticks.HairHaeJoBackend.entity.ArticleRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final S3UploadService s3UploadService;
    public void post(MultipartFile before, MultipartFile after, MakeArticleDto articleDto, Long currentMemberId) throws IOException {
        if (before.isEmpty()){
            if(after.isEmpty()) {
                articleRepository.save(articleDto.toArticle(currentMemberId,null,null));
            }
            else articleRepository.save(articleDto.toArticle(currentMemberId,null,s3UploadService.upload(after)));
        }
        else {
            if(after.isEmpty()){
                articleRepository.save(articleDto.toArticle(currentMemberId,s3UploadService.upload(before),null));
            }
            else articleRepository.save(articleDto.toArticle(currentMemberId,s3UploadService.upload(before),s3UploadService.upload(after)));
        }

    }
}
