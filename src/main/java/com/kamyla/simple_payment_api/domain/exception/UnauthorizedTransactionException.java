package com.kamyla.simple_payment_api.domain.exception;

public class UnauthorizedTransactionException extends RuntimeException {
    public UnauthorizedTransactionException() {
        super("Transação não autorizada.");
    }
}
