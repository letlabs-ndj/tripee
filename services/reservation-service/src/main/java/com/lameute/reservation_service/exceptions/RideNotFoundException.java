package com.lameute.reservation_service.exceptions;

public class RideNotFoundException extends RuntimeException{
    public RideNotFoundException(String msg){
        super(msg);
    }
}
