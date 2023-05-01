package Chopsticks.HairHaeJoBackend.entity;

import Chopsticks.HairHaeJoBackend.dto.article.ArticlelistResponseDto;

import java.util.List;

public interface ArticleRepositoryCustom {
    List<ArticlelistResponseDto> listfilter(String region,String category);
}
