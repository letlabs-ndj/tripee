package com.lameute.expedition_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PacketRequest (
        @NotNull(message = "Description required")
        String description,

        @Positive(message = "Weight must be positive")
        Double weight,

        @Positive(message = "Length must be positive")
        Double length,

        @Positive(message = "Width must be positive")
        Double width,

        @Positive(message = "Height must be positive")
        Double height
){
}
