package com.awesomelld.stackoverflow.repository;

import com.awesomelld.stackoverflow.entity.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {
    Optional<AnswerEntity> findByIdAndQuestion_Id(Long id, Long questionId);
}
