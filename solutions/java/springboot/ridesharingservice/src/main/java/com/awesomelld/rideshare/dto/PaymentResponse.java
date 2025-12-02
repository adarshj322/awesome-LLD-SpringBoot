package com.awesomelld.rideshare.dto;

import com.awesomelld.rideshare.enums.PaymentMethod;
import com.awesomelld.rideshare.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentResponse(
        Long id,
        Long tripId,
        PaymentMethod method,
        PaymentStatus status,
        BigDecimal amount,
        Instant paidAt
) {}
