package com.lameute.ride_service.dto;

public record UserResponse(
        long id,
        String email,
        String phoneNumber
) {
}
