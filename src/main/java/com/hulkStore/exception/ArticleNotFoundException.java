package com.hulkStore.exception;

public class ArticleNotFoundException extends BusinessException {
    public ArticleNotFoundException(int articleCode) {
        super(String.format("Article %d not found", articleCode));
    }
}
