package com.awesomelld.vendingmachine.repository;

import com.awesomelld.vendingmachine.entity.VendingMachineEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VendingMachineRepository extends JpaRepository<VendingMachineEntity, Long> {

    @EntityGraph(attributePaths = {"slots", "slots.product"})
    Optional<VendingMachineEntity> findByCode(String code);
}
