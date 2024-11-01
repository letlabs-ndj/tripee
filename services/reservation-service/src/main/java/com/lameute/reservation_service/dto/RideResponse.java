package com.lameute.reservation_service.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record RideResponse(
        long id,
        LocalDate departureDate,
        LocalTime departureTime,
        Place departurePlace,
        Place arrivalPlace,
        UserResponse user
) {
}
