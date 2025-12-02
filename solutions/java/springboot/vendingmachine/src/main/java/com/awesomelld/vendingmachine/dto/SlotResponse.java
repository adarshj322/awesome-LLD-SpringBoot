package com.awesomelld.vendingmachine.dto;

public record SlotResponse(
        Long id,
        String position,
        int capacity,
        int quantity,
        ProductSummary product
) {
}
