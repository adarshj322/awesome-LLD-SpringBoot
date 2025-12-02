package com.awesomelld.social.repository;

import com.awesomelld.social.entity.PostEntity;
import com.awesomelld.social.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    List<PostEntity> findByAuthor(UserEntity author);
}
