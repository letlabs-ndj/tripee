package com.lameute.account_service.dto;

public record UserResponse(
        long id,
        String username,
        String email,
        String phoneNumber
) {
}
