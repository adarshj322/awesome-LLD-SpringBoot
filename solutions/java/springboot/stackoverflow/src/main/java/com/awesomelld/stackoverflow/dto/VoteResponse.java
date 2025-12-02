package com.awesomelld.stackoverflow.dto;

public record VoteResponse(
        Long targetId,
        int voteScore
) {
}
