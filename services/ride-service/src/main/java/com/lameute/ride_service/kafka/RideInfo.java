package com.lameute.ride_service.kafka;

import com.lameute.ride_service.dto.UserResponse;

import java.util.List;

public record RideInfo(
        List<UserResponse> users,
        String departurePlace,
        String arrivalPlace
) {
}
