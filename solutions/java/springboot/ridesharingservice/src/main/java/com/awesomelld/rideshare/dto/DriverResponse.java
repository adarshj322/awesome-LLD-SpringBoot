package com.awesomelld.rideshare.dto;

import com.awesomelld.rideshare.enums.DriverStatus;

import java.math.BigDecimal;

public record DriverResponse(
        Long id,
        String name,
        String phone,
        DriverStatus status,
        BigDecimal rating,
        Double lat,
        Double lng
) {}
