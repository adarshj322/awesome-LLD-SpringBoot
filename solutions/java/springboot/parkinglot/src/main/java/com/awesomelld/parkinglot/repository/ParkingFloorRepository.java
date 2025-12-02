package com.awesomelld.parkinglot.repository;

import com.awesomelld.parkinglot.entity.ParkingFloorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingFloorRepository extends JpaRepository<ParkingFloorEntity, Long> {
}
