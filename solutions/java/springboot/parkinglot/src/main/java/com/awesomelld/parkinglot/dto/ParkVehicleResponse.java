package com.awesomelld.parkinglot.dto;

import com.awesomelld.parkinglot.enums.VehicleSize;

import java.time.Instant;
import java.util.UUID;

public record ParkVehicleResponse(
        UUID ticketId,
        String spotLabel,
        VehicleSize spotSize,
        Instant entryTime
) {
}
