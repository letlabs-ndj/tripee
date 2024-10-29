package com.lameute.expedition_service.dto;

import com.lameute.expedition_service.model.Enums.ExpeditionStatus;
import com.lameute.expedition_service.model.Packet;

import java.time.LocalDate;
import java.time.LocalTime;

public record ExpeditionResponse(
        long id,
        LocalDate expeditionDate,
        LocalTime expeditionTime,
        String emailToContact,
        Double price,
        ExpeditionStatus expeditionStatus,
        Packet packet,
        UserResponse user
) {
}
