package com.awesomelld.atm.repository;

import com.awesomelld.atm.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<CardEntity, Long> {
    Optional<CardEntity> findByCardNumber(String cardNumber);
}
