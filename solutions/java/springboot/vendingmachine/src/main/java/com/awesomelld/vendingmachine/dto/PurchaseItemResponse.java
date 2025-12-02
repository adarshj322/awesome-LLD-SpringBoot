package com.awesomelld.vendingmachine.dto;

import java.math.BigDecimal;

public record PurchaseItemResponse(
        Long slotId,
        String slotPosition,
        String productName,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal totalPrice
) {
}
