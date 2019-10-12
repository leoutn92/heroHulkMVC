package com.hulkStore.service;

import com.hulkStore.model.Article;
import com.hulkStore.exception.ArticleNotFoundException;
import com.hulkStore.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    public void addArticle(Article article) {
        articleRepository.save(article);
    }


    public Iterable<Article> getArticles() {
        return articleRepository.findAll();
    }

    public Article findArticle(int article_code) throws ArticleNotFoundException {
        Article article = articleRepository.findOne(article_code);
        if (article == null) {
            throw new ArticleNotFoundException(article.getArticleCode());
        }
        return article;
    }
}
