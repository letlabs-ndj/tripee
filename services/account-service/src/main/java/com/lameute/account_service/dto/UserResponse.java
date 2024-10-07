package com.lameute.account_service.dto;

public record UserResponse(
        String username,
        String email,
        String phoneNumber
) {
}
