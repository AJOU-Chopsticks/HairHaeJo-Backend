package Chopsticks.HairHaeJoBackend.entity;

import Chopsticks.HairHaeJoBackend.dto.article.ArticlelistResponseDto;

import java.util.Collection;
import java.util.List;

public interface ArticleRepositoryCustom {
    Collection<ArticlelistResponseDto> listfilter(String region, String category);
}
