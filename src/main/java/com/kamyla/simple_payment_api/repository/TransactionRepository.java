package com.kamyla.simple_payment_api.repository;

import com.kamyla.simple_payment_api.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
