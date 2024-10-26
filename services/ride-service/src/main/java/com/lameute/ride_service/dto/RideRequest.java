package com.lameute.ride_service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.LocalTime;

public record RideRequest (
        @NotNull(message = "Departure date required")
        LocalDate departureDate,

        @NotNull(message = "Departure time required")
        @JsonFormat(pattern = "HH:mm")
        LocalTime departureTime,

        @NotNull(message = "Departure place required")
        PlaceRequest departurePlace,

        @NotNull(message = "Arrival place required")
        PlaceRequest arrivalPlace,

        @NotNull(message = "Vehicle required")
        VehicleRequest vehicle,

        @Positive(message = "Price must be positive")
        Double price,

        @Positive(message = "Available places must be positive")
        Integer availablePlaces,

        @NotNull(message = "expedion status must be specified")
        Boolean doExpedition,

        @Positive(message = "Max weight must be positive")
        Double maxWeight,

        @NotNull(message = "UserId required")
        long userId
){
}
