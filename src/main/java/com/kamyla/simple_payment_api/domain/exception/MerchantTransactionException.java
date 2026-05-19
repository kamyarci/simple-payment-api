package com.kamyla.simple_payment_api.domain.exception;

public class MerchantTransactionException extends RuntimeException {
    public MerchantTransactionException() {
        super("Usuário do tipo lojista não está autorizado a realizar transações.");
    }
}
