package com.awesomelld.parkinglot.dto;

import java.util.List;

public record ParkingFloorResponse(
        Long id,
        int number,
        List<ParkingSpotResponse> spots
) {
}
