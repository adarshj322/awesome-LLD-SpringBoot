package com.awesomelld.rideshare.dto;

import com.awesomelld.rideshare.enums.PaymentMethod;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PaymentRequest(
        @NotNull(message = "tripId is required")
        Long tripId,
        @NotNull(message = "amount is required")
        @DecimalMin(value = "0.01", message = "amount must be positive")
        BigDecimal amount,
        @NotNull(message = "method is required")
        PaymentMethod method
) {}
