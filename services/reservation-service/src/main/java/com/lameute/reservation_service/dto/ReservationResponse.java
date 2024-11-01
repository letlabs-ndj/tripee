package com.lameute.reservation_service.dto;

import com.lameute.reservation_service.model.Enums.ReservationStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(
        long id,
        long rideId,
        LocalDate reservationDate,
        LocalTime reservationTime,
        Integer reservedPlaces,
        Double price,
        ReservationStatus reservationStatus,
        Boolean hasLuggage,
        String departurePlace,
        String arrivalPLace,
        UserResponse user
) {
}
