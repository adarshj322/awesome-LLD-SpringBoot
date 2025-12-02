package com.awesomelld.vendingmachine.dto;

import com.awesomelld.vendingmachine.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentLineResponse(
        Long id,
        PaymentMethod method,
        String denomination,
        BigDecimal amount,
        Instant capturedAt
) {
}
