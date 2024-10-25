package com.lameute.expedition_service.dto;

public record UserResponse(
        long id,
        String email,
        String phoneNumber
) {
}
