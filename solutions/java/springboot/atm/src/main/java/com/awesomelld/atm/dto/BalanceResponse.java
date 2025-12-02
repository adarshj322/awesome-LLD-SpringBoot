package com.awesomelld.atm.dto;

import java.math.BigDecimal;

public record BalanceResponse(
        BigDecimal balance
) {
}
