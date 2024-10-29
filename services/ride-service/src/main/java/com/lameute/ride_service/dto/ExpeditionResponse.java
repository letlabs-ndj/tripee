package com.lameute.ride_service.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ExpeditionResponse(
        long id,
        LocalDate expeditionDate,
        LocalTime expeditionTime,
        String emailToContact,
        ExpeditionStatus expeditionStatus,
        Double price,
        UserResponse user
) {
}
