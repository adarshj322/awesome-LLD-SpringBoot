package com.awesomelld.atm.repository;

import com.awesomelld.atm.entity.AtmEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AtmRepository extends JpaRepository<AtmEntity, Long> {
    Optional<AtmEntity> findByCode(String code);
}
