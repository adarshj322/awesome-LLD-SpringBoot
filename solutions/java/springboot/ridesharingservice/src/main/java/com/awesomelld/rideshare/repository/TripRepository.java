package com.awesomelld.rideshare.repository;

import com.awesomelld.rideshare.entity.TripEntity;
import com.awesomelld.rideshare.enums.TripStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TripRepository extends JpaRepository<TripEntity, Long> {
    List<TripEntity> findByRider_Id(Long riderId);
    List<TripEntity> findByDriver_Id(Long driverId);
    List<TripEntity> findByStatus(TripStatus status);
}
