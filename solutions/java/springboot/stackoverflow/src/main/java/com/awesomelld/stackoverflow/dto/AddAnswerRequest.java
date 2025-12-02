package com.awesomelld.stackoverflow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddAnswerRequest(
        @NotBlank(message = "Body is required")
        String body,
        @NotNull(message = "Author id is required")
        Long authorId
) {
}
