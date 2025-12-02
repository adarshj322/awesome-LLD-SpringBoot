package com.awesomelld.atm.repository;

import com.awesomelld.atm.entity.AtmSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AtmSessionRepository extends JpaRepository<AtmSessionEntity, Long> {
    Optional<AtmSessionEntity> findByToken(UUID token);
}
