package com.lameute.expedition_service.exceptions;

public class RideNotFoundException extends RuntimeException{
    public RideNotFoundException(String msg){
        super(msg);
    }
}