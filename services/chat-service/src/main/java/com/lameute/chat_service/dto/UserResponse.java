package com.lameute.chat_service.dto;

public record UserResponse(
        long id,
        String username,
        String password,
        String email,
        String phoneNumber
) {
}
