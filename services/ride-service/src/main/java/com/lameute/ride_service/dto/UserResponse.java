package com.lameute.ride_service.dto;

public record UserResponse(
        long id,
        String username,
        String email,
        String phoneNumber
) {
}
