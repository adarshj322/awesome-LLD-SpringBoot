package com.awesomelld.parkinglot.dto;

import java.util.List;

public record ParkingLotSummaryResponse(
        Long id,
        String code,
        String displayName,
        List<ParkingFloorResponse> floors
) {
}
