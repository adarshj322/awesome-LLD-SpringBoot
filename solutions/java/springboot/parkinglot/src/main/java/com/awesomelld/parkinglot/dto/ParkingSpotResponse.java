package com.awesomelld.parkinglot.dto;

import com.awesomelld.parkinglot.enums.SpotStatus;
import com.awesomelld.parkinglot.enums.VehicleSize;

public record ParkingSpotResponse(
        Long id,
        String label,
        VehicleSize vehicleSize,
        SpotStatus status,
        int floorNumber
) {
}
