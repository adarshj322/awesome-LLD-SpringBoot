package com.awesomelld.parkinglot.repository;

import com.awesomelld.parkinglot.entity.ParkingLotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkingLotRepository extends JpaRepository<ParkingLotEntity, Long> {
    Optional<ParkingLotEntity> findByCode(String code);
}
