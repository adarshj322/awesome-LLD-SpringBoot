package com.awesomelld.social.repository;

import com.awesomelld.social.entity.FollowEntity;
import com.awesomelld.social.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<FollowEntity, Long> {
    List<FollowEntity> findByFollower(UserEntity follower);
    List<FollowEntity> findByFollowee(UserEntity followee);
    Optional<FollowEntity> findByFollowerAndFollowee(UserEntity follower, UserEntity followee);
}
