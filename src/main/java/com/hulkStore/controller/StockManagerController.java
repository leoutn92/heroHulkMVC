package com.hulkStore.controller;

import com.hulkStore.model.Article;
import com.hulkStore.model.Hero;
import com.hulkStore.model.Transaction;
import com.hulkStore.exception.ArticleNotFoundException;
import com.hulkStore.exception.EmptyTransactionException;
import com.hulkStore.service.ArticleService;
import com.hulkStore.service.HeroService;
import com.hulkStore.service.TransactionService;
import com.hulkStore.transformer.ArticleTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static com.hulkStore.model.ChangeType.PRICE;
import static com.hulkStore.model.ChangeType.STOCK;

@Controller
@RequestMapping("/manager")
//Se dividieron las features en 2 users el salesman (encargado de las ventas) y el stock manager (encargado de administrar ek stock)
public class StockManagerController {
    public static final String DEFAULT_ERROR_MESSAGE = "Sorry we are current unavailable to process your request";

    @Autowired
    private HeroService heroService;

    @Autowired
    private ArticleTransformer articleTransformer;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private TransactionService transactionService;

    @RequestMapping(method = RequestMethod.GET)
    ModelAndView home() {
        ModelAndView modelAndView = getModelAndViewForHome();
        return modelAndView;
    };


    @RequestMapping(value= "/article", method = RequestMethod.POST)
    ModelAndView addArticle(@RequestParam String heroName, @RequestParam String article_type,
                            @RequestParam String size, @RequestParam int stock,
                            @RequestParam float price, @RequestParam String description) {
        Hero hero = heroService.findByName(heroName);
        articleService.addArticle(Article.builder()
                .hero(hero)
                .articleType(article_type)
                .size(size)
                .stock(stock)
                .price(price)
                .description(description)
                .build());
        ModelAndView modelAndView = getModelAndViewForHome();
        return modelAndView;
    };

    @RequestMapping(value= "/change/{atributeToChange}/{articleCode}", method = RequestMethod.GET)
    ModelAndView retrieveArticleForPriceChange(@PathVariable("atributeToChange") String atributeToChange,
                                               @PathVariable("articleCode") int articleCode) {
        ModelAndView modelAndView = new ModelAndView(atributeToChange + "ChangeTransaction");
        return findArticle(articleCode, modelAndView);
    }

    // Se plantearon 2 cambios en el inventario de articulos relacionados con precio y stock
    // Cada uno de estos endpoinis permite traquear como y por que se cambio el precio de un articulo
    @RequestMapping(value= "/change/price/{articleCode}", method = RequestMethod.POST)
    ModelAndView changeArticlePrice(@PathVariable("articleCode") int articleCode,
                                    float newPrice, String reason) {
        ModelAndView modelAndView = new ModelAndView("priceChangeTransaction");
        return handlePriceChange(articleCode, newPrice, reason, modelAndView);
    }

    @RequestMapping(value= "/change/stock/{articleCode}", method = RequestMethod.POST)
    ModelAndView changeArticleStock(@PathVariable("articleCode") int articleCode,
                                    int newStock, String reason) {
        ModelAndView modelAndView = new ModelAndView("stockChangeTransaction");
        return handleStockChange(articleCode, newStock, reason, modelAndView);
    }

    private ModelAndView findArticle(@PathVariable("articleCode") int articleCode, ModelAndView modelAndView) {
        Article article = null;
        try {
            article = articleService.findArticle(articleCode);
            modelAndView.addObject("article", articleTransformer.transform(article));
        } catch (ArticleNotFoundException e) {
            modelAndView.addObject("error", e.getMessage());
        }
        return modelAndView;
    }

    private ModelAndView getModelAndViewForHome() {
        Iterable<Article> articles = articleService.getArticles();
        ModelAndView modelAndView = new ModelAndView("manager");
        modelAndView.addObject("heroes", heroService.getHeroes());
        modelAndView.addObject("articles", articleTransformer.transform(articles));
        return modelAndView;
    }

    private ModelAndView handlePriceChange(int articleCode, float newPrice, String reason,
                                           ModelAndView modelAndView) {
        Transaction transaction = Transaction.builder()
                .article(Article.builder().articleCode(articleCode).build())
                .price(newPrice)
                .type(PRICE.name())
                .reason(reason)
                .build();
        return handleTransaction(modelAndView, transaction);
    }

    private ModelAndView handleStockChange(int articleCode, int newStock, String reason,
                                           ModelAndView modelAndView) {
        Transaction transaction = Transaction.builder()
                .article(Article.builder().articleCode(articleCode).build())
                .reason(reason)
                .stock(newStock)
                .type(STOCK.name())
                .build();
        return handleTransaction(modelAndView, transaction);
    }

    private ModelAndView handleTransaction(ModelAndView modelAndView, Transaction transaction) {
        Article article = Article.builder().build();
        modelAndView.addObject("article", article);
        try {
            Transaction transactionSaved = transactionService.handleTransaction(transaction);
            modelAndView.addObject("article", articleTransformer.transform(transactionSaved.getArticle()));
            modelAndView.addObject("message", "Transaction Success");
        } catch (ArticleNotFoundException | EmptyTransactionException e) {
            modelAndView.addObject("error", e.getMessage());
        } catch (Exception e) {
            modelAndView.addObject("error", DEFAULT_ERROR_MESSAGE);
        }
        return modelAndView;
    }
}
