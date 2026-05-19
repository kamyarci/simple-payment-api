package com.kamyla.simple_payment_api.dto;

import java.math.BigDecimal;

public record TransactionDTO(BigDecimal value, Long payer, Long payee) {
}
