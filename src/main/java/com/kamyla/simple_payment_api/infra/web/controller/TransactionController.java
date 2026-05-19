package com.kamyla.simple_payment_api.infra.web.controller;

import com.kamyla.simple_payment_api.dto.TransactionDTO;
import com.kamyla.simple_payment_api.domain.transaction.Transaction;
import com.kamyla.simple_payment_api.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        Transaction newTransaction = transactionService.createTransaction(transactionDTO);
        return ResponseEntity.ok(newTransaction);
    }

}
