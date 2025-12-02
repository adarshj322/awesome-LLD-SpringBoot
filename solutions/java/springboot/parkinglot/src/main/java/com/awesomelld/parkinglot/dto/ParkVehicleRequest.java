package com.awesomelld.parkinglot.dto;

import com.awesomelld.parkinglot.enums.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ParkVehicleRequest(
        @NotBlank(message = "Vehicle number is required")
        String vehicleNumber,
        @NotNull(message = "Vehicle type is required")
        VehicleType vehicleType
) {
}
