package com.awesomelld.social.repository;

import com.awesomelld.social.entity.FeedItemEntity;
import com.awesomelld.social.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedItemRepository extends JpaRepository<FeedItemEntity, Long> {
    List<FeedItemEntity> findByUserOrderByDeliveredAtDesc(UserEntity user);
}
