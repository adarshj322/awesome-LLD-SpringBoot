package com.awesomelld.atm.dto;

import com.awesomelld.atm.enums.TransactionStatus;
import com.awesomelld.atm.enums.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;

public record TransactionResponse(
        Long transactionId,
        TransactionType type,
        TransactionStatus status,
        BigDecimal amount,
        BigDecimal balanceAfter,
        Instant createdAt
) {
}
