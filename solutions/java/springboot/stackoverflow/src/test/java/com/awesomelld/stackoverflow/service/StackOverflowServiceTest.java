package com.awesomelld.stackoverflow.service;

import com.awesomelld.stackoverflow.dto.AcceptAnswerRequest;
import com.awesomelld.stackoverflow.dto.AddAnswerRequest;
import com.awesomelld.stackoverflow.dto.AnswerResponse;
import com.awesomelld.stackoverflow.dto.CreateQuestionRequest;
import com.awesomelld.stackoverflow.dto.QuestionDetailResponse;
import com.awesomelld.stackoverflow.dto.VoteRequest;
import com.awesomelld.stackoverflow.dto.VoteResponse;
import com.awesomelld.stackoverflow.enums.VoteType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class StackOverflowServiceTest {

    @Autowired
    private StackOverflowService service;

    @Test
    void createQuestion_shouldPersistWithTags() {
        QuestionDetailResponse response = service.createQuestion(
                new CreateQuestionRequest("Design a cache", "Looking for cache eviction strategies", 1L, List.of("java", "cache")));

        assertThat(response.id()).isNotNull();
        assertThat(response.tags()).contains("cache");
        assertThat(response.authorId()).isEqualTo(1L);
    }

    @Test
    void addAnswer_shouldReturnAnswerResponse() {
        AnswerResponse response = service.addAnswer(1L, new AddAnswerRequest("Consider CQRS for this.", 2L));

        assertThat(response.id()).isNotNull();
        assertThat(response.body()).contains("CQRS");
        assertThat(response.authorId()).isEqualTo(2L);
    }

    @Test
    void voteQuestion_shouldUpdateScore() {
        VoteResponse firstVote = service.voteQuestion(1L, new VoteRequest(3L, VoteType.UPVOTE));
        VoteResponse switched = service.voteQuestion(1L, new VoteRequest(3L, VoteType.DOWNVOTE));

        assertThat(firstVote.voteScore()).isNotEqualTo(switched.voteScore());
    }

    @Test
    void acceptAnswer_shouldMarkAnswerAccepted() {
        AnswerResponse response = service.acceptAnswer(1L, 2L, new AcceptAnswerRequest(1L));

        assertThat(response.accepted()).isTrue();
    }
}
