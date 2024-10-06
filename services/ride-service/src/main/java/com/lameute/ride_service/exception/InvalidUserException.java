package com.lameute.ride_service.exception;

public class InvalidUserException extends RuntimeException{
    public InvalidUserException(String msg){
        super(msg);
    }
}
