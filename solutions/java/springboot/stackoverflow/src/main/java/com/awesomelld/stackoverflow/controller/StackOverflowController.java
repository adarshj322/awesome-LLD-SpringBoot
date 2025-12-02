package com.awesomelld.stackoverflow.controller;

import com.awesomelld.stackoverflow.dto.AcceptAnswerRequest;
import com.awesomelld.stackoverflow.dto.AddAnswerRequest;
import com.awesomelld.stackoverflow.dto.AnswerResponse;
import com.awesomelld.stackoverflow.dto.CommentRequest;
import com.awesomelld.stackoverflow.dto.CommentResponse;
import com.awesomelld.stackoverflow.dto.CreateQuestionRequest;
import com.awesomelld.stackoverflow.dto.QuestionDetailResponse;
import com.awesomelld.stackoverflow.dto.QuestionSummaryResponse;
import com.awesomelld.stackoverflow.dto.VoteRequest;
import com.awesomelld.stackoverflow.dto.VoteResponse;
import com.awesomelld.stackoverflow.service.StackOverflowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class StackOverflowController {

    private final StackOverflowService service;

    /**
     * Returns the most recent questions together with vote counts and tags.
     */
    @GetMapping
    public List<QuestionSummaryResponse> listQuestions() {
        return service.listQuestions();
    }

    /**
     * Returns a question, its comments, and the full answer thread.
     */
    @GetMapping("/{questionId}")
    public QuestionDetailResponse getQuestion(@PathVariable Long questionId) {
        return service.getQuestion(questionId);
    }

    /**
     * Creates a new question authored by an existing user.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionDetailResponse createQuestion(@Valid @RequestBody CreateQuestionRequest request) {
        return service.createQuestion(request);
    }

    /**
     * Adds an answer under the specified question.
     */
    @PostMapping("/{questionId}/answers")
    @ResponseStatus(HttpStatus.CREATED)
    public AnswerResponse addAnswer(@PathVariable Long questionId, @Valid @RequestBody AddAnswerRequest request) {
        return service.addAnswer(questionId, request);
    }

    /**
     * Adds a comment directly to the given question.
     */
    @PostMapping("/{questionId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse addQuestionComment(@PathVariable Long questionId, @Valid @RequestBody CommentRequest request) {
        return service.addQuestionComment(questionId, request);
    }

    /**
     * Adds a comment under an answer.
     */
    @PostMapping("/{questionId}/answers/{answerId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse addAnswerComment(@PathVariable Long questionId,
                                            @PathVariable Long answerId,
                                            @Valid @RequestBody CommentRequest request) {
        return service.addAnswerComment(questionId, answerId, request);
    }

    /**
     * Casts or updates a vote for the given question.
     */
    @PostMapping("/{questionId}/votes")
    public VoteResponse voteQuestion(@PathVariable Long questionId, @Valid @RequestBody VoteRequest request) {
        return service.voteQuestion(questionId, request);
    }

    /**
     * Casts or updates a vote for an answer.
     */
    @PostMapping("/{questionId}/answers/{answerId}/votes")
    public VoteResponse voteAnswer(@PathVariable Long questionId,
                                   @PathVariable Long answerId,
                                   @Valid @RequestBody VoteRequest request) {
        return service.voteAnswer(questionId, answerId, request);
    }

    /**
     * Marks an answer as accepted. Only the question author may call this.
     */
    @PatchMapping("/{questionId}/answers/{answerId}/accept")
    public AnswerResponse acceptAnswer(@PathVariable Long questionId,
                                       @PathVariable Long answerId,
                                       @Valid @RequestBody AcceptAnswerRequest request) {
        return service.acceptAnswer(questionId, answerId, request);
    }
}
