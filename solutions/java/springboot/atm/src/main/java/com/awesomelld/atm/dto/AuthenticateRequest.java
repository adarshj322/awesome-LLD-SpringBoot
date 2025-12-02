package com.awesomelld.atm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthenticateRequest(
        @NotBlank(message = "Card number is required")
        String cardNumber,
        @NotBlank(message = "PIN is required")
        @Size(min = 4, max = 8, message = "PIN must be between 4 and 8 digits")
        String pin
) {
}
