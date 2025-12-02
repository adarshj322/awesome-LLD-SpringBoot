package com.awesomelld.stackoverflow.dto;

import java.time.Instant;
import java.util.List;

public record QuestionDetailResponse(
        Long id,
        String title,
        String body,
        Instant createdAt,
        Long authorId,
        String authorName,
        int voteScore,
        List<String> tags,
        List<CommentResponse> comments,
        List<AnswerResponse> answers
) {
}
