package com.lameute.reservation_service.dto;

import com.lameute.reservation_service.model.Enums.ReservationStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

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
