package com.awesomelld.parkinglot.entity;

import com.awesomelld.parkinglot.enums.SpotStatus;
import com.awesomelld.parkinglot.enums.VehicleSize;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "parking_spot")
public class ParkingSpotEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 32)
    private String label;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_size", nullable = false, length = 16)
    private VehicleSize vehicleSize;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private SpotStatus status = SpotStatus.AVAILABLE;

    @ManyToOne(optional = false)
    @JoinColumn(name = "floor_id", nullable = false)
    private ParkingFloorEntity floor;

    public ParkingSpotEntity(String label, VehicleSize vehicleSize) {
        this.label = label;
        this.vehicleSize = vehicleSize;
        this.status = SpotStatus.AVAILABLE;
    }
}
