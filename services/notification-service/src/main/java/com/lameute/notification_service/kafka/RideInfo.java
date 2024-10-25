package com.lameute.notification_service.kafka;

import java.util.List;

public record RideInfo(
        List<UserResponse> passengers,
        List<UserResponse> expeditors,
        String departurePlace,
        String arrivalPlace
) {
}
