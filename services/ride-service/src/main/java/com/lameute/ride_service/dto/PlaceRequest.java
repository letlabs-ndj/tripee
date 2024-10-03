package com.lameute.ride_service.dto;

import jakarta.validation.constraints.NotNull;

public record PlaceRequest(
    @NotNull(message = "Place name required")
    String name,

    @NotNull(message = "Place town required")
    String town,

    @NotNull(message = "Place longitute required")
    String longitude,

    @NotNull(message = "Place latitude required")
    String latitude,
    
    String details
) {
    
}
