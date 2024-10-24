package com.lameute.notification_service.kafka;

import java.util.List;

public record PassengerList(
        List<UserResponse> users
) {
}
