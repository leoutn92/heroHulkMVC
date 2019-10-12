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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static java.util.Arrays.*;
import static java.util.Collections.singleton;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class SaleServiceTest {
    @Captor
    ArgumentCaptor<Sale> saleCaptor;

    @Mock
    private ArticleRepository mockArticleRepository;

    @Mock
    private ArticleStockDecorator mockArticleStockDecorator;

    @Mock
    private SaleRepository mockSaleRepository;

    private SaleService saleService;

    private static Date DATE_TEST = new Date();

    private Article article1 = Article.builder().articleCode(1).price(10).stock(1).build();
    private Article article2 = Article.builder().articleCode(2).price(20).stock(2).build();

    private ArticleSold articleSold1;

    private ArticleSold articleSold2 = ArticleSold.builder()
            .amountSold(2)
            .articleCode(2)
            .unitPrice(20).build();

    private Set<ArticleSold> articlesSold;

    private Sale sale;

    private Iterable<Sale> sales;


    @Before
    public void setup() {
        articleSold1 = ArticleSold.builder()
                .amountSold(1)
                .articleCode(1)
                .unitPrice(10).build();

        articlesSold = new HashSet<>(asList(articleSold1, articleSold2));

        sale = Sale.builder().saleCode(1)
                .saleDate(DATE_TEST)
                .articleSolds(articlesSold).build();
        sales = singleton(sale);

        saleService = new SaleService(mockArticleStockDecorator, mockArticleRepository, mockSaleRepository);
    }

    @Test
    public void shouldGetSales() {
        when(mockSaleRepository.findAll()).thenReturn(sales);
        assertEquals(saleService.getSales(), sales);
        verify(mockSaleRepository).findAll();
    }

    @Test
    public void shouldSoldArticles() throws ArticleNotFoundException, SoldOutArticleException, PriceChangeOnSaleException {
        sale = Sale.builder().saleCode(1)
                .saleDate(DATE_TEST)
                .totalSalePrice(50)
                .articleSolds(articlesSold).build();

        when(mockArticleRepository.findOne(1)).thenReturn(article1);
        when(mockArticleRepository.findOne(2)).thenReturn(article2);
        when(mockSaleRepository.save(any(Sale.class))).thenReturn(sale);

        assertEquals(saleService.addSale(asList(articleSold1, articleSold2)), sale);

        verify(mockArticleStockDecorator).decorateStock(article1, articleSold1);
        verify(mockArticleStockDecorator).decorateStock(article2, articleSold2);

        verify(mockSaleRepository).save(saleCaptor.capture());

        assertEquals(saleCaptor.getValue().getArticleSolds(), articlesSold);
        assertTrue(saleCaptor.getValue().getTotalSalePrice() == 50.0);
    }

    @Test(expected = ArticleNotFoundException.class)
    public void shouldTrownArticleNotFoundException() throws ArticleNotFoundException, SoldOutArticleException, PriceChangeOnSaleException {
        when(mockArticleRepository.findOne(1)).thenReturn(null);
        saleService.addSale(asList(articleSold1, articleSold2));
    }

    @Test(expected = SoldOutArticleException.class)
    public void shouldTrownSoldOutArticleException() throws ArticleNotFoundException, SoldOutArticleException, PriceChangeOnSaleException {
        articleSold1.setAmountSold(3);
        when(mockArticleRepository.findOne(1)).thenReturn(article1);
        saleService.addSale(asList(articleSold1, articleSold2));
    }


    @Test(expected = PriceChangeOnSaleException.class)
    public void shouldTrownPriceChangeOnSaleException() throws ArticleNotFoundException, SoldOutArticleException, PriceChangeOnSaleException {
        articleSold1.setUnitPrice(29);
        when(mockArticleRepository.findOne(1)).thenReturn(article1);
        saleService.addSale(asList(articleSold1, articleSold2));
    }
}