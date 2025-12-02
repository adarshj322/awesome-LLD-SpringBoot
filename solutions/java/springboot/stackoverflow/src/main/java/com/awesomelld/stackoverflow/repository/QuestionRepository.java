package com.awesomelld.stackoverflow.repository;

import com.awesomelld.stackoverflow.entity.QuestionEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

    @EntityGraph(attributePaths = {"author", "tags", "answers", "answers.author", "answers.comments", "answers.comments.author", "comments", "comments.author"})
    Optional<QuestionEntity> findDetailedById(Long id);
}
