package com.awesomelld.parkinglot.support;

import com.awesomelld.parkinglot.enums.VehicleSize;
import com.awesomelld.parkinglot.enums.VehicleType;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class VehicleSizePolicy {

    private final Map<VehicleType, List<VehicleSize>> mapping = new EnumMap<>(VehicleType.class);

    public VehicleSizePolicy() {
        mapping.put(VehicleType.BIKE, List.of(VehicleSize.SMALL, VehicleSize.MEDIUM, VehicleSize.LARGE));
        mapping.put(VehicleType.CAR, List.of(VehicleSize.MEDIUM, VehicleSize.LARGE));
        mapping.put(VehicleType.TRUCK, List.of(VehicleSize.LARGE));
    }

    public List<VehicleSize> allowedSizes(VehicleType type) {
        return mapping.getOrDefault(type, List.of());
    }
}
