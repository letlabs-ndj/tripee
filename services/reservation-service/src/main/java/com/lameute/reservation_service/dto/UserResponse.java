package com.lameute.reservation_service.dto;

public record UserResponse(
        long id,
        String email,
        String phoneNumber
) {
}
