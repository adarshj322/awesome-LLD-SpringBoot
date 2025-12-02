package com.awesomelld.social.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank(message = "username is required")
        @Size(max = 64, message = "username too long")
        String username,
        @NotBlank(message = "email is required")
        @Email(message = "email must be valid")
        @Size(max = 128, message = "email too long")
        String email,
        @NotBlank(message = "displayName is required")
        @Size(max = 128, message = "displayName too long")
        String displayName,
        @Size(max = 256, message = "bio too long")
        String bio
) {}
