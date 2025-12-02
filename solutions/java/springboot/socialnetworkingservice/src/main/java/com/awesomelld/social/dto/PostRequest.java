package com.awesomelld.social.dto;

import com.awesomelld.social.enums.Visibility;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostRequest(
        @NotNull(message = "authorId is required")
        Long authorId,
        @NotBlank(message = "content is required")
        @Size(max = 2048, message = "content too long")
        String content,
        Visibility visibility
) {}
