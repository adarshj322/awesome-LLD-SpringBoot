package com.awesomelld.stackoverflow.dto;

import java.time.Instant;

public record CommentResponse(
        Long id,
        String body,
        Instant createdAt,
        Long authorId,
        String authorName
) {
}
