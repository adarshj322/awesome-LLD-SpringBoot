package com.awesomelld.vendingmachine.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record StartSessionRequest(
        @NotNull(message = "slotId is required")
        Long slotId,
        @Min(value = 1, message = "quantity must be positive")
        int quantity
) {
}
