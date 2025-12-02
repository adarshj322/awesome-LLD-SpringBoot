package com.awesomelld.social.dto;

public record UserResponse(
        Long id,
        String username,
        String displayName,
        String bio
) {}
