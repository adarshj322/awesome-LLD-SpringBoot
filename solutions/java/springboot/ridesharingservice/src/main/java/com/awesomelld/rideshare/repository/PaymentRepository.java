package com.awesomelld.rideshare.repository;

import com.awesomelld.rideshare.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
}
