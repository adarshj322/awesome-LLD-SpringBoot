package com.awesomelld.rideshare.dto;

import jakarta.validation.constraints.NotNull;

public record TripRequest(
        @NotNull(message = "riderId is required")
        Long riderId,
        @NotNull(message = "pickupLat is required")
        Double pickupLat,
        @NotNull(message = "pickupLng is required")
        Double pickupLng,
        @NotNull(message = "dropLat is required")
        Double dropLat,
        @NotNull(message = "dropLng is required")
        Double dropLng
) {}
