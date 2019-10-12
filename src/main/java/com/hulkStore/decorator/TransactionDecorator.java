package com.hulkStore.decorator;

import com.hulkStore.model.Article;
import com.hulkStore.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionDecorator {
    public void decoratePrice(Transaction transaction, Article article) {
        transaction.setPriceDifference(transaction.getPrice() - article.getPrice());
    }

    public void decorateStock(Transaction transaction, Article article) {
        transaction.setStockDifference(transaction.getStock() - article.getStock());
    }
}
