package com.lameute.ride_service.kafka;

import com.lameute.ride_service.dto.UserResponse;

import java.util.List;

public record PassengerList(
        List<UserResponse> users
) {
}
