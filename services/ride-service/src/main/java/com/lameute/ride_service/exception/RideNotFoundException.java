package com.lameute.ride_service.exception;

public class RideNotFoundException extends RuntimeException{
    public RideNotFoundException(String msg){
        super(msg);
    }
}
