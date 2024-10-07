package com.lameute.reservation_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ReservationRequest(
        @Positive(message = "Reserved places must be positive")
        Integer reservedPlaces,

        @Positive(message = "Reserved places must be positive")
        Double price,

        @NotNull(message = "Precise the presence of luggage")
        Boolean hasLuggage,

        @NotNull(message = "User id required")
        Long userId,

        @NotNull(message = "Ride id required")
        long rideId
) {
}
