package com.awesomelld.stackoverflow.dto;

import jakarta.validation.constraints.NotNull;

public record AcceptAnswerRequest(
        @NotNull(message = "Actor id is required")
        Long actorId
) {
}
