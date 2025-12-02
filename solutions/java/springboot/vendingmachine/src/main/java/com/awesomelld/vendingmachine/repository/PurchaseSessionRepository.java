package com.awesomelld.vendingmachine.repository;

import com.awesomelld.vendingmachine.entity.PurchaseSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PurchaseSessionRepository extends JpaRepository<PurchaseSessionEntity, Long> {
    Optional<PurchaseSessionEntity> findByIdAndMachine_Code(Long id, String machineCode);
}
