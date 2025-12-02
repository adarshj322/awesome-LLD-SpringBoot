package com.awesomelld.rideshare.repository;

import com.awesomelld.rideshare.entity.DriverEntity;
import com.awesomelld.rideshare.enums.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriverRepository extends JpaRepository<DriverEntity, Long> {
    List<DriverEntity> findByStatus(DriverStatus status);
}
