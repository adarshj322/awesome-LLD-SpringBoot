package com.awesomelld.vendingmachine.dto;

import com.awesomelld.vendingmachine.enums.PurchaseState;

import java.math.BigDecimal;

public record RefundResponse(
        Long sessionId,
        PurchaseState state,
        BigDecimal amountRefunded
) {
}
