package com.awesomelld.atm.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record SessionResponse(
        UUID sessionId,
        String atmCode,
        String accountNumber,
        String accountHolder,
        BigDecimal balance
) {
}
