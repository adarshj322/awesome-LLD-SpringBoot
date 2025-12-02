package com.awesomelld.stackoverflow.mapper;

import com.awesomelld.stackoverflow.dto.AnswerResponse;
import com.awesomelld.stackoverflow.dto.CommentResponse;
import com.awesomelld.stackoverflow.dto.QuestionDetailResponse;
import com.awesomelld.stackoverflow.dto.QuestionSummaryResponse;
import com.awesomelld.stackoverflow.entity.AnswerEntity;
import com.awesomelld.stackoverflow.entity.CommentEntity;
import com.awesomelld.stackoverflow.entity.QuestionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface StackOverflowMapper {

    @Mapping(target = "authorId", source = "entity.author.id")
    @Mapping(target = "authorName", source = "entity.author.displayName")
    @Mapping(target = "voteScore", source = "voteScore")
    @Mapping(target = "answerCount", expression = "java(entity.getAnswers() != null ? entity.getAnswers().size() : 0)")
    @Mapping(target = "tags", source = "entity", qualifiedByName = "mapTags")
    QuestionSummaryResponse toQuestionSummary(QuestionEntity entity, int voteScore);

    @Mapping(target = "authorId", source = "entity.author.id")
    @Mapping(target = "authorName", source = "entity.author.displayName")
    @Mapping(target = "voteScore", source = "voteScore")
    @Mapping(target = "tags", source = "entity", qualifiedByName = "mapTags")
    @Mapping(target = "comments", source = "entity", qualifiedByName = "mapQuestionComments")
    @Mapping(target = "answers", ignore = true)
    QuestionDetailResponse toQuestionDetail(QuestionEntity entity, int voteScore);

    @Mapping(target = "authorId", source = "entity.author.id")
    @Mapping(target = "authorName", source = "entity.author.displayName")
    @Mapping(target = "voteScore", source = "voteScore")
    @Mapping(target = "comments", source = "entity", qualifiedByName = "mapAnswerComments")
    AnswerResponse toAnswerResponse(AnswerEntity entity, int voteScore);

    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "authorName", source = "author.displayName")
    CommentResponse toCommentResponse(CommentEntity entity);

    @Named("mapTags")
    default List<String> mapTags(QuestionEntity entity) {
        return entity.getTags()
                .stream()
                .map(tag -> tag.getName())
                .sorted()
                .collect(Collectors.toList());
    }

    @Named("mapQuestionComments")
    default List<CommentResponse> mapQuestionComments(QuestionEntity entity) {
        return entity.getComments().stream()
                .map(this::toCommentResponse)
                .toList();
    }

    @Named("mapAnswerComments")
    default List<CommentResponse> mapAnswerComments(AnswerEntity entity) {
        return entity.getComments().stream()
                .map(this::toCommentResponse)
                .toList();
    }
}
