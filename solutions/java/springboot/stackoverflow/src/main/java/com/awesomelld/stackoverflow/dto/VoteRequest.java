package com.awesomelld.stackoverflow.dto;

import com.awesomelld.stackoverflow.enums.VoteType;
import jakarta.validation.constraints.NotNull;

public record VoteRequest(
        @NotNull(message = "Voter id is required")
        Long voterId,
        @NotNull(message = "Vote type is required")
        VoteType type
) {
}
