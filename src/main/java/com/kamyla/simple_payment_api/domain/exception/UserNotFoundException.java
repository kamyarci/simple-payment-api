package com.kamyla.simple_payment_api.domain.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Usuário não encontrado: " + id);
    }
}
