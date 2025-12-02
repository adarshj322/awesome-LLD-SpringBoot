package com.awesomelld.social.dto;

import java.time.Instant;

public record FeedItemResponse(
        Long postId,
        Long authorId,
        String authorName,
        String content,
        Instant deliveredAt
) {}
