package com.kamyla.simple_payment_api.infra.exception;

import com.kamyla.simple_payment_api.domain.exception.*;
import com.kamyla.simple_payment_api.dto.ExceptionDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionDTO> treatDuplicateEntry(DataIntegrityViolationException exception) {
        return ResponseEntity.badRequest().body(new ExceptionDTO("Usuário já cadastrado", "400"));
    }

    @ExceptionHandler({EntityNotFoundException.class, UserNotFoundException.class})
    public ResponseEntity<ExceptionDTO> treat404(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionDTO(exception.getMessage(), "404"));
    }

    @ExceptionHandler({MerchantTransactionException.class, UnauthorizedTransactionException.class})
    public ResponseEntity<ExceptionDTO> treatForbidden(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionDTO(exception.getMessage(), "403"));
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ExceptionDTO> treatInsufficientBalance(InsufficientBalanceException exception) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ExceptionDTO(exception.getMessage(), "422"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDTO> treatGeneralExceptions(Exception exception) {
        return ResponseEntity.internalServerError().body(new ExceptionDTO(exception.getMessage(), "500"));
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ExceptionDTO> treatDuplicateUser(DuplicateUserException exception) {
        return ResponseEntity.badRequest().body(new ExceptionDTO(exception.getMessage(), "400"));
    }
}
