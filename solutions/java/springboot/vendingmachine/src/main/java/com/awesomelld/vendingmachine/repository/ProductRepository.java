package com.awesomelld.vendingmachine.repository;

import com.awesomelld.vendingmachine.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
