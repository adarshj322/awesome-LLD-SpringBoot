package com.awesomelld.parkinglot.dto;

import com.awesomelld.parkinglot.enums.TicketStatus;
import com.awesomelld.parkinglot.enums.VehicleType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TicketResponse(
        UUID ticketId,
        String vehicleNumber,
        VehicleType vehicleType,
        Instant entryTime,
        Instant exitTime,
        TicketStatus status,
        BigDecimal fee,
        ParkingSpotResponse spot
) {
}
