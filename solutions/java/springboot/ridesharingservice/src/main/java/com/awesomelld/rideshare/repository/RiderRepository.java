package com.awesomelld.rideshare.repository;

import com.awesomelld.rideshare.entity.RiderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiderRepository extends JpaRepository<RiderEntity, Long> {
}
