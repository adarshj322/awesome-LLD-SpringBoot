package com.awesomelld.stackoverflow.dto;

import java.time.Instant;
import java.util.List;

public record QuestionSummaryResponse(
        Long id,
        String title,
        Instant createdAt,
        Long authorId,
        String authorName,
        int voteScore,
        int answerCount,
        List<String> tags
) {
}
