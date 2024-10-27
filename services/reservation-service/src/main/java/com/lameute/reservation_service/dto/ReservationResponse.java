package com.lameute.reservation_service.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(
        long id,
        LocalDate reservationDate,
        LocalTime reservationTime,
        Integer reservedPlaces,
        Double price,
        Boolean hasLuggage,
        UserResponse user
) {
}
