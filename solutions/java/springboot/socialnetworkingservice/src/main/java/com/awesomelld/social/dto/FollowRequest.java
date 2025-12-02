package com.awesomelld.social.dto;

import jakarta.validation.constraints.NotNull;

public record FollowRequest(
        @NotNull(message = "followerId is required")
        Long followerId,
        @NotNull(message = "followeeId is required")
        Long followeeId
) {}
