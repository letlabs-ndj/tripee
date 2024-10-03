package com.lameute.ride_service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.LocalTime;

public record RideRequest (
        @NotNull(message = "Departure date required")
        LocalDate departureDate,

        @NotNull(message = "Departure time required")
        LocalTime departureTime,

        @NotNull(message = "Departure place required")
        PlaceRequest departurePlace,

        @NotNull(message = "Arrival place required")
        PlaceRequest arrivalPlace,

        @Positive(message = "Price must be positive")
        double price,

        @Positive(message = "Available places must be positive")
        int availablePlaces,

        @NotNull(message = "UserId required")
        long userId
){
}
