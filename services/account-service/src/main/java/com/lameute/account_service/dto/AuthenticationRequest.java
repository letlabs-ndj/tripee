package com.lameute.account_service.dto;

import jakarta.validation.constraints.NotNull;

public record AuthenticationRequest(
        @NotNull(message = "User's email required")
        String email,

        @NotNull(message = "User's password required")
        String password
) {
}
