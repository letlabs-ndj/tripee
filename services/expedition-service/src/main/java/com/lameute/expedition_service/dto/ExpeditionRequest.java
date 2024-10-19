package com.lameute.expedition_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ExpeditionRequest(
        @Positive(message = "Reserved places must be positive")
        Double price,

        @NotNull(message = "EmailToContact required")
        String emailToContact,

        @NotNull(message = "Packet required")
        PacketRequest packet,

        @NotNull(message = "User id required")
        Long userId,

        @NotNull(message = "Ride id required")
        long rideId
) {
}
