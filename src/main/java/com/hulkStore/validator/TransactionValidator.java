package com.hulkStore.validator;

import com.hulkStore.model.Article;
import com.hulkStore.model.Transaction;
import com.hulkStore.exception.ArticleNotFoundException;
import com.hulkStore.exception.EmptyTransactionException;
import org.springframework.stereotype.Component;

@Component
public class TransactionValidator {
    public void validatePriceChange(Transaction transaction, Article article) throws EmptyTransactionException, ArticleNotFoundException {
        if (article == null) {
            throw new ArticleNotFoundException(article.getArticleCode());
        }

        if (transaction.getPrice() == article.getPrice()) {
            throw new EmptyTransactionException();
        }
    }

    public void validateStockChange(Transaction transaction, Article article) throws EmptyTransactionException, ArticleNotFoundException {
        if (article == null) {
            throw new ArticleNotFoundException(article.getArticleCode());
        }

        if (transaction.getStock() == article.getStock()) {
            throw new EmptyTransactionException();
        }
    }
}
