package com.awesomelld.vendingmachine.repository;

import com.awesomelld.vendingmachine.entity.SlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SlotRepository extends JpaRepository<SlotEntity, Long> {
    Optional<SlotEntity> findByIdAndMachine_Code(Long id, String code);
}
