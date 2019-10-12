package com.hulkStore.exception;

// El objetivo de esta exception es englobar todas las exceptiones relacionadas con reglas de negocio
public class BusinessException extends Exception {
    public BusinessException(String message) {
        super(message);
    }
}
