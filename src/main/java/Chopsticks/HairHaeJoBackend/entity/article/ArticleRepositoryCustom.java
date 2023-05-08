package Chopsticks.HairHaeJoBackend.entity.article;

import Chopsticks.HairHaeJoBackend.dto.article.ArticlelistResponseDto;

import java.util.Collection;
import java.util.List;

public interface ArticleRepositoryCustom {
    Collection<ArticlelistResponseDto> listfilter(String region, String category,String gender,String tag);
}
