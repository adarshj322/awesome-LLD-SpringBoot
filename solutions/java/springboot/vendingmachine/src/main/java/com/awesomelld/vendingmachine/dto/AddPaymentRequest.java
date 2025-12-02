package com.awesomelld.vendingmachine.dto;

import com.awesomelld.vendingmachine.enums.PaymentMethod;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AddPaymentRequest(
        @NotNull(message = "Payment method is required")
        PaymentMethod method,
        @DecimalMin(value = "0.01", message = "Amount must be positive")
        BigDecimal amount,
        @NotBlank(message = "Denomination is required")
        String denomination
) {
}
