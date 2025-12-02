package com.awesomelld.stackoverflow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateQuestionRequest(
        @NotBlank(message = "Title is required")
        String title,
        @NotBlank(message = "Body is required")
        String body,
        @NotNull(message = "Author id is required")
        Long authorId,
        @NotEmpty(message = "At least one tag is required")
        List<@NotBlank String> tags
) {
}
