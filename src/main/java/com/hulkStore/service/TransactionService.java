package com.hulkStore.service;

import com.hulkStore.decorator.TransactionDecorator;
import com.hulkStore.model.Article;
import com.hulkStore.model.ChangeType;
import com.hulkStore.model.Transaction;
import com.hulkStore.exception.ArticleNotFoundException;
import com.hulkStore.exception.EmptyTransactionException;
import com.hulkStore.repository.ArticleRepository;
import com.hulkStore.repository.TransactionRepository;
import com.hulkStore.validator.TransactionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionValidator transactionValidator;

    @Autowired
    private TransactionDecorator transactionDecorator;

    public Transaction handleTransaction(Transaction transaction) throws ArticleNotFoundException, EmptyTransactionException {
        Transaction transactionCompleted = null;
        if (ChangeType.PRICE.name().equals(transaction.getType())) {
            transactionCompleted = this.changePrice(transaction);
        };

        if (ChangeType.STOCK.name().equals(transaction.getType())) {
            transactionCompleted = this.changeStock(transaction);
        };

        return transactionCompleted;
    }

    private Transaction changePrice(Transaction transaction) throws ArticleNotFoundException, EmptyTransactionException {
        Article articleToChange = articleRepository.findOne(transaction.getArticle().getArticleCode());
        transactionValidator.validatePriceChange(transaction, articleToChange);
        transactionDecorator.decoratePrice(transaction, articleToChange);
        articleToChange.setPrice(transaction.getPrice());
        return processTransaction(articleToChange, transaction);
    };

    private Transaction changeStock(Transaction transaction) throws ArticleNotFoundException, EmptyTransactionException {
        Article articleToChange = articleRepository.findOne(transaction.getArticle().getArticleCode());
        transactionValidator.validateStockChange(transaction, articleToChange);
        transactionDecorator.decorateStock(transaction, articleToChange);
        articleToChange.setStock(transaction.getStock());
        return processTransaction(articleToChange, transaction);
    };


    private Transaction processTransaction(Article article, Transaction transaction) {
        Article articleSaved =  articleRepository.save(article);
        transaction.setArticle(articleSaved);
        return transactionRepository.save(transaction);
    }


}
