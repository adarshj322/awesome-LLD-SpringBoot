package com.awesomelld.social.repository;

import com.awesomelld.social.entity.LikeEntity;
import com.awesomelld.social.entity.PostEntity;
import com.awesomelld.social.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    long countByPost(PostEntity post);
    Optional<LikeEntity> findByPostAndUser(PostEntity post, UserEntity user);
}
