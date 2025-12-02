package com.awesomelld.social.repository;

import com.awesomelld.social.entity.CommentEntity;
import com.awesomelld.social.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByPost(PostEntity post);
}
