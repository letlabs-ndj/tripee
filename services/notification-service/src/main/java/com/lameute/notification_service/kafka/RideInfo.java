package com.lameute.notification_service.kafka;

import java.util.List;

public record RideInfo(
        List<UserResponse> users,
        String departurePlace,
        String arrivalPlace
) {
}
