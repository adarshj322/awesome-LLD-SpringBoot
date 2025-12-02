package com.awesomelld.parkinglot.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ReleaseVehicleResponse(
        UUID ticketId,
        BigDecimal fee,
        Instant exitTime
) {
}
