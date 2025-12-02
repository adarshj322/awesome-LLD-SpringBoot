package com.awesomelld.stackoverflow.mapper;

import com.awesomelld.stackoverflow.dto.AnswerResponse;
import com.awesomelld.stackoverflow.dto.CommentResponse;
import com.awesomelld.stackoverflow.dto.QuestionDetailResponse;
import com.awesomelld.stackoverflow.dto.QuestionSummaryResponse;
import com.awesomelld.stackoverflow.entity.AnswerEntity;
import com.awesomelld.stackoverflow.entity.CommentEntity;
import com.awesomelld.stackoverflow.entity.QuestionEntity;
import com.awesomelld.stackoverflow.entity.UserEntity;
import java.time.Instant;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-02T18:36:03+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.7 (Azul Systems, Inc.)"
)
@Component
public class StackOverflowMapperImpl implements StackOverflowMapper {

    @Override
    public QuestionSummaryResponse toQuestionSummary(QuestionEntity entity, int voteScore) {
        if ( entity == null ) {
            return null;
        }

        Long authorId = null;
        String authorName = null;
        List<String> tags = null;
        Long id = null;
        String title = null;
        Instant createdAt = null;
        if ( entity != null ) {
            authorId = entityAuthorId( entity );
            authorName = entityAuthorDisplayName( entity );
            tags = mapTags( entity );
            id = entity.getId();
            title = entity.getTitle();
            createdAt = entity.getCreatedAt();
        }
        int voteScore1 = 0;
        voteScore1 = voteScore;

        int answerCount = entity.getAnswers() != null ? entity.getAnswers().size() : 0;

        QuestionSummaryResponse questionSummaryResponse = new QuestionSummaryResponse( id, title, createdAt, authorId, authorName, voteScore1, answerCount, tags );

        return questionSummaryResponse;
    }

    @Override
    public QuestionDetailResponse toQuestionDetail(QuestionEntity entity, int voteScore) {
        if ( entity == null ) {
            return null;
        }

        Long authorId = null;
        String authorName = null;
        List<String> tags = null;
        List<CommentResponse> comments = null;
        Long id = null;
        String title = null;
        String body = null;
        Instant createdAt = null;
        if ( entity != null ) {
            authorId = entityAuthorId( entity );
            authorName = entityAuthorDisplayName( entity );
            tags = mapTags( entity );
            comments = mapQuestionComments( entity );
            id = entity.getId();
            title = entity.getTitle();
            body = entity.getBody();
            createdAt = entity.getCreatedAt();
        }
        int voteScore1 = 0;
        voteScore1 = voteScore;

        List<AnswerResponse> answers = null;

        QuestionDetailResponse questionDetailResponse = new QuestionDetailResponse( id, title, body, createdAt, authorId, authorName, voteScore1, tags, comments, answers );

        return questionDetailResponse;
    }

    @Override
    public AnswerResponse toAnswerResponse(AnswerEntity entity, int voteScore) {
        if ( entity == null ) {
            return null;
        }

        Long authorId = null;
        String authorName = null;
        List<CommentResponse> comments = null;
        Long id = null;
        String body = null;
        Instant createdAt = null;
        boolean accepted = false;
        if ( entity != null ) {
            authorId = entityAuthorId1( entity );
            authorName = entityAuthorDisplayName1( entity );
            comments = mapAnswerComments( entity );
            id = entity.getId();
            body = entity.getBody();
            createdAt = entity.getCreatedAt();
            accepted = entity.isAccepted();
        }
        int voteScore1 = 0;
        voteScore1 = voteScore;

        AnswerResponse answerResponse = new AnswerResponse( id, body, createdAt, accepted, voteScore1, authorId, authorName, comments );

        return answerResponse;
    }

    @Override
    public CommentResponse toCommentResponse(CommentEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long authorId = null;
        String authorName = null;
        Long id = null;
        String body = null;
        Instant createdAt = null;

        authorId = entityAuthorId2( entity );
        authorName = entityAuthorDisplayName2( entity );
        id = entity.getId();
        body = entity.getBody();
        createdAt = entity.getCreatedAt();

        CommentResponse commentResponse = new CommentResponse( id, body, createdAt, authorId, authorName );

        return commentResponse;
    }

    private Long entityAuthorId(QuestionEntity questionEntity) {
        if ( questionEntity == null ) {
            return null;
        }
        UserEntity author = questionEntity.getAuthor();
        if ( author == null ) {
            return null;
        }
        Long id = author.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityAuthorDisplayName(QuestionEntity questionEntity) {
        if ( questionEntity == null ) {
            return null;
        }
        UserEntity author = questionEntity.getAuthor();
        if ( author == null ) {
            return null;
        }
        String displayName = author.getDisplayName();
        if ( displayName == null ) {
            return null;
        }
        return displayName;
    }

    private Long entityAuthorId1(AnswerEntity answerEntity) {
        if ( answerEntity == null ) {
            return null;
        }
        UserEntity author = answerEntity.getAuthor();
        if ( author == null ) {
            return null;
        }
        Long id = author.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityAuthorDisplayName1(AnswerEntity answerEntity) {
        if ( answerEntity == null ) {
            return null;
        }
        UserEntity author = answerEntity.getAuthor();
        if ( author == null ) {
            return null;
        }
        String displayName = author.getDisplayName();
        if ( displayName == null ) {
            return null;
        }
        return displayName;
    }

    private Long entityAuthorId2(CommentEntity commentEntity) {
        if ( commentEntity == null ) {
            return null;
        }
        UserEntity author = commentEntity.getAuthor();
        if ( author == null ) {
            return null;
        }
        Long id = author.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entityAuthorDisplayName2(CommentEntity commentEntity) {
        if ( commentEntity == null ) {
            return null;
        }
        UserEntity author = commentEntity.getAuthor();
        if ( author == null ) {
            return null;
        }
        String displayName = author.getDisplayName();
        if ( displayName == null ) {
            return null;
        }
        return displayName;
    }
}
