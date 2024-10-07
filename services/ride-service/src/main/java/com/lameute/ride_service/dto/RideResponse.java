package com.lameute.ride_service.dto;

import com.lameute.ride_service.model.Place;
import com.lameute.ride_service.model.Vehicle;

import java.time.LocalDate;
import java.time.LocalTime;

public record RideResponse(
        LocalDate departureDate,
        LocalTime departureTime,
        Place departurePlace,
        Place arrivalPlace,
        Vehicle vehicle,
        Double price,
        Integer availablePlaces,
        Boolean doExpedition,
        Double maxWeight,
        UserResponse user
) {
}
