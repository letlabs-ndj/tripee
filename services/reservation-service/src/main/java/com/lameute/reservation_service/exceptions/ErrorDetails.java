package com.lameute.reservation_service.exceptions;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ErrorDetails {
    private LocalDate timestamp;
    private String message;
    private String details;

    public ErrorDetails(LocalDate timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}
