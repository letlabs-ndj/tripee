package com.lameute.account_service.dto;

import jakarta.validation.constraints.NotNull;

public record UserRequest(
    @NotNull(message = "User's username required")
    String username,

    @NotNull(message = "User's email required")
    String email,

    @NotNull(message = "User's password required")
    String password,

    @NotNull(message = "User's phone number required")
    String phoneNumber
) {
}
