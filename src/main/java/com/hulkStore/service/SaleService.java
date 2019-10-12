package com.hulkStore.service;

import com.hulkStore.decorator.ArticleStockDecorator;
import com.hulkStore.exception.ArticleNotFoundException;
import com.hulkStore.exception.PriceChangeOnSaleException;
import com.hulkStore.exception.SoldOutArticleException;
import com.hulkStore.model.Article;
import com.hulkStore.model.ArticleSold;
import com.hulkStore.model.Sale;
import com.hulkStore.repository.ArticleRepository;
import com.hulkStore.repository.SaleRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class SaleService {
    @Autowired
    private ArticleStockDecorator articleStockDecorator;

    @Autowired
    private ArticleRepository articleRepository;;

    @Autowired
    private SaleRepository saleRepository;

    public Sale addSale(List<ArticleSold> articlesSold) throws SoldOutArticleException, ArticleNotFoundException, PriceChangeOnSaleException {
        return processSale(articlesSold);
    }

    public Iterable<Sale> getSales() {
        return saleRepository.findAll();
    }

    private Sale processSale(List<ArticleSold> articlesToSale) throws ArticleNotFoundException, SoldOutArticleException, PriceChangeOnSaleException {
        List<Article> articles = new ArrayList<>();
        float totalSalePrice = 0;

        for (ArticleSold articleToSale: articlesToSale) {
            Article article = checkArticleAvailability(articleToSale);
            totalSalePrice += articleToSale.getTotalPrice();
            articleStockDecorator.decorateStock(article, articleToSale);
            articleToSale.setArticle(article);
            articles.add(article);
        }

        articleRepository.save(articles);

        Sale sale = saleRepository.save(Sale.builder()
                .articleSolds(new HashSet<>(articlesToSale))
                .totalSalePrice(totalSalePrice)
                .build());

        return sale;
    }

    private Article checkArticleAvailability(ArticleSold articleSold) throws ArticleNotFoundException, SoldOutArticleException, PriceChangeOnSaleException {
        Article article = articleRepository.findOne(articleSold.getArticleCode());

        if (article == null) {
            throw new ArticleNotFoundException(articleSold.getArticleCode());
        }

        if (article.getStock()<articleSold.getAmountSold()) {
            throw new SoldOutArticleException(article.getArticleCode());
        }

        if (article.getPrice() != articleSold.getUnitPrice()) {
            throw new PriceChangeOnSaleException(article.getArticleCode());
        }


        return article;
    }


}
