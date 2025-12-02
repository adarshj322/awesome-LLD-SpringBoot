package com.awesomelld.stackoverflow.service;

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
import com.awesomelld.stackoverflow.entity.AnswerEntity;
import com.awesomelld.stackoverflow.entity.CommentEntity;
import com.awesomelld.stackoverflow.entity.QuestionEntity;
import com.awesomelld.stackoverflow.entity.TagEntity;
import com.awesomelld.stackoverflow.entity.UserEntity;
import com.awesomelld.stackoverflow.entity.VoteEntity;
import com.awesomelld.stackoverflow.enums.PostType;
import com.awesomelld.stackoverflow.enums.VoteType;
import com.awesomelld.stackoverflow.exception.InvalidActionException;
import com.awesomelld.stackoverflow.exception.ResourceNotFoundException;
import com.awesomelld.stackoverflow.mapper.StackOverflowMapper;
import com.awesomelld.stackoverflow.repository.AnswerRepository;
import com.awesomelld.stackoverflow.repository.CommentRepository;
import com.awesomelld.stackoverflow.repository.QuestionRepository;
import com.awesomelld.stackoverflow.repository.TagRepository;
import com.awesomelld.stackoverflow.repository.UserRepository;
import com.awesomelld.stackoverflow.repository.VoteRepository;
import com.awesomelld.stackoverflow.support.ReputationPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StackOverflowService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final CommentRepository commentRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;
    private final StackOverflowMapper mapper;
    private final ReputationPolicy reputationPolicy;

    public List<QuestionSummaryResponse> listQuestions() {
        return questionRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).stream()
                .map(question -> mapper.toQuestionSummary(question, voteRepository.scoreForQuestion(question.getId())))
                .toList();
    }

    public QuestionDetailResponse getQuestion(Long id) {
        QuestionEntity question = questionRepository.findDetailedById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question %d not found".formatted(id)));
        return buildQuestionDetail(question);
    }

    @Transactional
    public QuestionDetailResponse createQuestion(CreateQuestionRequest request) {
        UserEntity author = getUser(request.authorId());

        QuestionEntity question = new QuestionEntity(author, request.title(), request.body());
        Set<TagEntity> tags = resolveTags(request.tags());
        question.setTags(tags);
        QuestionEntity saved = questionRepository.save(question);
        return getQuestion(saved.getId());
    }

    @Transactional
    public AnswerResponse addAnswer(Long questionId, AddAnswerRequest request) {
        QuestionEntity question = getQuestionEntity(questionId);
        UserEntity author = getUser(request.authorId());
        AnswerEntity answer = new AnswerEntity(author, question, request.body());
        question.getAnswers().add(answer);
        AnswerEntity saved = answerRepository.save(answer);
        return mapper.toAnswerResponse(saved, voteRepository.scoreForAnswer(saved.getId()));
    }

    @Transactional
    public CommentResponse addQuestionComment(Long questionId, CommentRequest request) {
        QuestionEntity question = getQuestionEntity(questionId);
        UserEntity author = getUser(request.authorId());
        CommentEntity comment = new CommentEntity(author, request.body(), PostType.QUESTION);
        comment.setQuestion(question);
        CommentEntity saved = commentRepository.save(comment);
        return mapper.toCommentResponse(saved);
    }

    @Transactional
    public CommentResponse addAnswerComment(Long questionId, Long answerId, CommentRequest request) {
        AnswerEntity answer = getAnswerEntity(questionId, answerId);
        UserEntity author = getUser(request.authorId());
        CommentEntity comment = new CommentEntity(author, request.body(), PostType.ANSWER);
        comment.setAnswer(answer);
        CommentEntity saved = commentRepository.save(comment);
        return mapper.toCommentResponse(saved);
    }

    @Transactional
    public VoteResponse voteQuestion(Long questionId, VoteRequest request) {
        QuestionEntity question = getQuestionEntity(questionId);
        UserEntity voter = getUser(request.voterId());
        VoteEntity vote = voteRepository.findByQuestion_IdAndVoter_Id(questionId, voter.getId())
                .orElseGet(() -> new VoteEntity(voter, request.type(), PostType.QUESTION));
        VoteType previous = vote.getId() == null ? null : vote.getType();
        vote.setQuestion(question);
        vote.setTargetType(PostType.QUESTION);
        vote.setType(request.type());
        voteRepository.save(vote);
        reputationPolicy.handleVoteChange(question.getAuthor(), previous, vote.getType(), PostType.QUESTION);
        return new VoteResponse(questionId, voteRepository.scoreForQuestion(questionId));
    }

    @Transactional
    public VoteResponse voteAnswer(Long questionId, Long answerId, VoteRequest request) {
        AnswerEntity answer = getAnswerEntity(questionId, answerId);
        UserEntity voter = getUser(request.voterId());
        VoteEntity vote = voteRepository.findByAnswer_IdAndVoter_Id(answerId, voter.getId())
                .orElseGet(() -> new VoteEntity(voter, request.type(), PostType.ANSWER));
        VoteType previous = vote.getId() == null ? null : vote.getType();
        vote.setAnswer(answer);
        vote.setTargetType(PostType.ANSWER);
        vote.setType(request.type());
        voteRepository.save(vote);
        reputationPolicy.handleVoteChange(answer.getAuthor(), previous, vote.getType(), PostType.ANSWER);
        return new VoteResponse(answerId, voteRepository.scoreForAnswer(answerId));
    }

    @Transactional
    public AnswerResponse acceptAnswer(Long questionId, Long answerId, AcceptAnswerRequest request) {
        QuestionEntity question = getQuestionEntity(questionId);
        if (!question.getAuthor().getId().equals(request.actorId())) {
            throw new InvalidActionException("Only the question author can accept an answer");
        }
        AnswerEntity answer = getAnswerEntity(questionId, answerId);
        question.getAnswers().forEach(ans -> ans.setAccepted(false));
        answer.setAccepted(true);
        reputationPolicy.handleAcceptedAnswer(answer.getAuthor());
        AnswerEntity saved = answerRepository.save(answer);
        return mapper.toAnswerResponse(saved, voteRepository.scoreForAnswer(answerId));
    }

    private QuestionDetailResponse buildQuestionDetail(QuestionEntity question) {
        int questionScore = voteRepository.scoreForQuestion(question.getId());
        QuestionDetailResponse base = mapper.toQuestionDetail(question, questionScore);
        List<AnswerResponse> answers = question.getAnswers().stream()
                .map(answer -> mapper.toAnswerResponse(answer, voteRepository.scoreForAnswer(answer.getId())))
                .toList();
        return new QuestionDetailResponse(base.id(), base.title(), base.body(), base.createdAt(), base.authorId(),
                base.authorName(), base.voteScore(), base.tags(), base.comments(), answers);
    }

    private UserEntity getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User %d not found".formatted(userId)));
    }

    private QuestionEntity getQuestionEntity(Long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question %d not found".formatted(questionId)));
    }

    private AnswerEntity getAnswerEntity(Long questionId, Long answerId) {
        return answerRepository.findByIdAndQuestion_Id(answerId, questionId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Answer %d not found for question %d".formatted(answerId, questionId)));
    }

    private Set<TagEntity> resolveTags(List<String> tagNames) {
        Set<TagEntity> tags = new HashSet<>();
        for (String rawName : new ArrayList<>(tagNames)) {
            String name = rawName.trim().toLowerCase(Locale.ROOT);
            TagEntity tag = tagRepository.findByName(name)
                    .orElseGet(() -> tagRepository.save(new TagEntity(name)));
            tags.add(tag);
        }
        return tags;
    }
}
