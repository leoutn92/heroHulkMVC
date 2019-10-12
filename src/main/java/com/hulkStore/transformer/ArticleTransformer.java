package com.hulkStore.transformer;

import com.hulkStore.dto.ArticleDTO;
import com.hulkStore.model.Article;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArticleTransformer {
    public List<ArticleDTO> transform(Iterable<Article> articles) {
        List<ArticleDTO> dtoArticles= new ArrayList<>();
        for (Article article: articles) {
            ArticleDTO articleDTO = transform(article);
            dtoArticles.add(articleDTO);
        }
        return dtoArticles;
    }

    public ArticleDTO transform(Article article) {
        return ArticleDTO.builder()
                .articleCode(article.getArticleCode())
                .articleType(article.getArticleType())
                .description(article.getDescription())
                .hero(article.getHero().getName())
                .price(article.getPrice())
                .stock(article.getStock())
                .size(article.getSize())
                .build();
    }
}
