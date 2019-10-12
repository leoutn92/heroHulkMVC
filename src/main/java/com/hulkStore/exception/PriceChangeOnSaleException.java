package com.hulkStore.exception;

public class PriceChangeOnSaleException extends BusinessException {
    public PriceChangeOnSaleException(int articleCode) {
        super(String.format("Price for article %d was changed on the middle of the transaction", articleCode));
    }
}
