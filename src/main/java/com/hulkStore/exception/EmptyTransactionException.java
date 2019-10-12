package com.hulkStore.exception;

// Esta exception se lanza si la transaction (cambio de precio o stock) no modifica ninguno de los valores actuales del articulo
public class EmptyTransactionException extends BusinessException {

    public static final String EMPTY_TRANSACTION_MESSAGE = "A transaction " +
            "suppose to change price/stock of an article";

    public EmptyTransactionException() {
        super(EMPTY_TRANSACTION_MESSAGE);
    }
}
