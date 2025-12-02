package com.awesomelld.rideshare.dto;

public record DriverAssignmentResponse(
        Long driverId,
        String driverName,
        Double driverLat,
        Double driverLng
) {}
