package com.lameute.ride_service.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(
        long id,
        LocalDate reservationDate,
        LocalTime reservationTime,
        Integer reservedPlaces,
        Double price,
        ReservationStatus reservationStatus,
        Boolean hasLuggage,
        UserResponse user
) {
}
