package com.awesomelld.stackoverflow.repository;

import com.awesomelld.stackoverflow.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<TagEntity, Long> {
    Optional<TagEntity> findByName(String name);

    List<TagEntity> findByNameIn(List<String> names);
}
