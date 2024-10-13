package com.lameute.reservation_service.exceptions;

public class InvalidRideException extends RuntimeException {
    public InvalidRideException(String message) {
        super(message);
    }
}
