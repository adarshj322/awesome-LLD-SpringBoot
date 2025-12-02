package com.awesomelld.vendingmachine.dto;

import com.awesomelld.vendingmachine.enums.PurchaseState;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record PurchaseSessionResponse(
        Long sessionId,
        String machineCode,
        PurchaseState state,
        BigDecimal totalCost,
        BigDecimal amountPaid,
        BigDecimal changeDue,
        Instant startedAt,
        Instant completedAt,
        List<PurchaseItemResponse> items,
        List<PaymentLineResponse> payments
) {
}
