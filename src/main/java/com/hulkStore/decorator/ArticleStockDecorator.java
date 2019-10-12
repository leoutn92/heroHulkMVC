package com.hulkStore.decorator;

import com.hulkStore.model.Article;
import com.hulkStore.model.ArticleSold;
import org.springframework.stereotype.Component;

@Component
public class ArticleStockDecorator {
    public void decorateStock(Article article, ArticleSold articleSold) {
        article.setStock(article.getStock() - articleSold.getAmountSold());
    }
}
