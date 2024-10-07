package com.lameute.reservation_service.exceptions;

public class ReservationNotFoundException extends RuntimeException{
    public ReservationNotFoundException(String msg){
        super(msg);
    }
}
