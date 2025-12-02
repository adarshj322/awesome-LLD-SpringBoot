package com.awesomelld.rideshare.dto;

import com.awesomelld.rideshare.enums.TripStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record TripResponse(
        Long id,
        TripStatus status,
        Long riderId,
        Long driverId,
        Double pickupLat,
        Double pickupLng,
        Double dropLat,
        Double dropLng,
        BigDecimal fareEstimate,
        BigDecimal fareFinal,
        Instant requestedAt,
        Instant startedAt,
        Instant completedAt
) {}
