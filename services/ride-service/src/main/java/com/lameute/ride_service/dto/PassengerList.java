package com.lameute.ride_service.dto;

import java.util.List;

public record PassengerList(
        List<UserResponse> users
) {
}
