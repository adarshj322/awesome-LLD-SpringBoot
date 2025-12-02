package com.awesomelld.vendingmachine.dto;

import java.math.BigDecimal;
import java.util.List;

public record DispenseResponse(
        Long sessionId,
        List<PurchaseItemResponse> dispensedItems,
        BigDecimal changeReturned
) {
}
