package com.awesomelld.vendingmachine.support;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class ChangeCalculator {

    private static final BigDecimal[] DENOMINATIONS = {
            new BigDecimal("5.00"),
            new BigDecimal("2.00"),
            new BigDecimal("1.00"),
            new BigDecimal("0.50"),
            new BigDecimal("0.25"),
            new BigDecimal("0.10"),
            new BigDecimal("0.05")
    };

    public Map<BigDecimal, Integer> makeChange(BigDecimal changeDue) {
        Map<BigDecimal, Integer> breakdown = new LinkedHashMap<>();
        BigDecimal remaining = changeDue.setScale(2, BigDecimal.ROUND_HALF_EVEN);

        for (BigDecimal denom : DENOMINATIONS) {
            int count = remaining.divideToIntegralValue(denom).intValue();
            if (count > 0) {
                breakdown.put(denom, count);
                remaining = remaining.subtract(denom.multiply(BigDecimal.valueOf(count)));
            }
        }
        if (remaining.compareTo(BigDecimal.ZERO) > 0) {
            breakdown.put(new BigDecimal("0.01"), remaining.multiply(new BigDecimal("100")).intValue());
        }
        return breakdown;
    }
}
