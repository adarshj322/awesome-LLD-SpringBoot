package com.awesomelld.parkinglot.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "parking_floor")
public class ParkingFloorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "floor_number", nullable = false)
    private int floorNumber;

    @ManyToOne(optional = false)
    @JoinColumn(name = "lot_id", nullable = false)
    private ParkingLotEntity lot;

    @OneToMany(mappedBy = "floor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParkingSpotEntity> spots = new ArrayList<>();

    public ParkingFloorEntity(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public void addSpot(ParkingSpotEntity spot) {
        spots.add(spot);
        spot.setFloor(this);
    }
}
