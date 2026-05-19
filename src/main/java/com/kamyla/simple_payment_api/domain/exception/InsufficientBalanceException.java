package com.kamyla.simple_payment_api.domain.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException() {
        super("Saldo insuficiente para realizar a transação.");
    }
}
