package com.awesomelld.stackoverflow.repository;

import com.awesomelld.stackoverflow.entity.VoteEntity;
import com.awesomelld.stackoverflow.enums.PostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<VoteEntity, Long> {

    Optional<VoteEntity> findByQuestion_IdAndVoter_Id(Long questionId, Long voterId);

    Optional<VoteEntity> findByAnswer_IdAndVoter_Id(Long answerId, Long voterId);

    @Query("select coalesce(sum(case when v.type = com.awesomelld.stackoverflow.enums.VoteType.UPVOTE then 1 else -1 end), 0) " +
            "from VoteEntity v where v.question.id = :questionId and v.targetType = com.awesomelld.stackoverflow.enums.PostType.QUESTION")
    int scoreForQuestion(Long questionId);

    @Query("select coalesce(sum(case when v.type = com.awesomelld.stackoverflow.enums.VoteType.UPVOTE then 1 else -1 end), 0) " +
            "from VoteEntity v where v.answer.id = :answerId and v.targetType = com.awesomelld.stackoverflow.enums.PostType.ANSWER")
    int scoreForAnswer(Long answerId);
}
