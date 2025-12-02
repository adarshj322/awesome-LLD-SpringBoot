package com.awesomelld.stackoverflow.dto;

import java.time.Instant;
import java.util.List;

public record AnswerResponse(
        Long id,
        String body,
        Instant createdAt,
        boolean accepted,
        int voteScore,
        Long authorId,
        String authorName,
        List<CommentResponse> comments
) {
}
