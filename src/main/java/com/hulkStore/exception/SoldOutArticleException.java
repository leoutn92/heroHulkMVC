package com.hulkStore.exception;

//El articulo que se intenta vendar se encuentra sin stock
public class SoldOutArticleException extends BusinessException {
    public SoldOutArticleException(int articleCode) {
        super(String.format("Article %d is sold out", articleCode));
    }
}
