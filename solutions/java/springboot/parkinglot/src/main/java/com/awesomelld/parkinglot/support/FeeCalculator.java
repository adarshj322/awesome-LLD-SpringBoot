package com.awesomelld.parkinglot.support;

import com.awesomelld.parkinglot.enums.VehicleType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.EnumMap;
import java.util.Map;

@Component
public class FeeCalculator {

    private static final Map<VehicleType, BigDecimal> HOURLY_RATES = new EnumMap<>(VehicleType.class);

    static {
        HOURLY_RATES.put(VehicleType.BIKE, new BigDecimal("10.00"));
        HOURLY_RATES.put(VehicleType.CAR, new BigDecimal("20.00"));
        HOURLY_RATES.put(VehicleType.TRUCK, new BigDecimal("35.00"));
    }

    public BigDecimal calculate(VehicleType vehicleType, Duration duration) {
        BigDecimal rate = HOURLY_RATES.get(vehicleType);
        if (rate == null) {
            throw new IllegalArgumentException("Unsupported vehicle type: " + vehicleType);
        }

        long minutes = Math.max(1, duration.toMinutes());
        long billableHours = Math.max(1, (long) Math.ceil(minutes / 60.0));
        return rate.multiply(BigDecimal.valueOf(billableHours)).setScale(2, RoundingMode.HALF_UP);
    }
}
