package com.lameute.notification_service.kafka;

public record UserResponse(
        long id,
        String email,
        String phoneNumber
) {
}
