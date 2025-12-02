package com.awesomelld.stackoverflow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentRequest(
        @NotBlank(message = "Comment body is required")
        String body,
        @NotNull(message = "Author id is required")
        Long authorId
) {
}
