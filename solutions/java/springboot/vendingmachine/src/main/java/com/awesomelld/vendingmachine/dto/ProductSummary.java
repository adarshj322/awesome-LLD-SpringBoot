package com.awesomelld.vendingmachine.dto;

import java.math.BigDecimal;

public record ProductSummary(
        Long id,
        String name,
        String category,
        BigDecimal price
) {
}
