package com.awesomelld.stackoverflow.repository;

import com.awesomelld.stackoverflow.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
