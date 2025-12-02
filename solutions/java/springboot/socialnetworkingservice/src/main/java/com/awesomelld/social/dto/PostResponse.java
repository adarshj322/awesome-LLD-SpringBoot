package com.awesomelld.social.dto;

import com.awesomelld.social.enums.Visibility;

import java.time.Instant;

public record PostResponse(
        Long id,
        Long authorId,
        String authorName,
        String content,
        Visibility visibility,
        Instant createdAt,
        long likeCount
) {}
