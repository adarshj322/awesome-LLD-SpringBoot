package com.awesomelld.social.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentRequest(
        @NotNull(message = "postId is required")
        Long postId,
        @NotNull(message = "authorId is required")
        Long authorId,
        @NotBlank(message = "content is required")
        String content
) {}
