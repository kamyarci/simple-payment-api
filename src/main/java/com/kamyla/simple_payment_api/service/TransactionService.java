package com.kamyla.simple_payment_api.service;

import com.kamyla.simple_payment_api.dto.TransactionDTO;
import com.kamyla.simple_payment_api.domain.exception.UnauthorizedTransactionException;
import com.kamyla.simple_payment_api.domain.transaction.Transaction;
import com.kamyla.simple_payment_api.domain.user.User;
import com.kamyla.simple_payment_api.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final UserService userService;
    private final TransactionRepository transactionRepository;
    private final RestClient restClient;
    private final NotificationService notificationService;

    @Transactional
    public Transaction createTransaction(TransactionDTO transactionDTO) {
        User sender = this.userService.findUserById(transactionDTO.payer());
        User receiver = this.userService.findUserById(transactionDTO.payee());

        userService.validateTransaction(sender, transactionDTO.value());

        if (!this.authorizeTransaction(sender, transactionDTO.value())) {
            throw new UnauthorizedTransactionException();
        }

        Transaction transaction = buildTransaction(transactionDTO, sender, receiver);
        transferBalance(sender, receiver, transactionDTO.value());

        this.transactionRepository.save(transaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        this.notificationService.sendNotification(sender, "Transação realizada com sucesso.");
        this.notificationService.sendNotification(receiver, "Transação recebida com sucesso.");

        return transaction;
    }

    private Transaction buildTransaction(TransactionDTO dto, User sender, User receiver) {
        Transaction transaction = new Transaction();
        transaction.setAmount(dto.value());
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setTimestamp(LocalDateTime.now());
        return transaction;
    }

    private void transferBalance(User sender, User receiver, BigDecimal value) {
        sender.setBalance(sender.getBalance().subtract(value));
        receiver.setBalance(receiver.getBalance().add(value));
    }

    public boolean authorizeTransaction(User sender, BigDecimal value) {
        try {
            Map<String, Object> body = restClient.get()
                    .uri("https://util.devi.tools/api/v2/authorize")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            if (body == null) return false;

            Object data = body.get("data");
            if (!(data instanceof Map<?, ?> dataMap)) return false;

            return Boolean.TRUE.equals(dataMap.get("authorization"));
        } catch (RestClientException e) {
            return false;
        }
    }

}
