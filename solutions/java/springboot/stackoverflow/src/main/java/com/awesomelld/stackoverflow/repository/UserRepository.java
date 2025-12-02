package com.awesomelld.stackoverflow.repository;

import com.awesomelld.stackoverflow.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
