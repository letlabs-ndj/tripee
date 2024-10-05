package com.lameute.ride_service.dto;

import jakarta.validation.constraints.NotNull;

public record VehicleRequest(
    @NotNull(message = "Vehicle brand required")
    String vehicleBrand,

    @NotNull(message = "Vehicle registration number required")
    String registrationNumber
) {

}
