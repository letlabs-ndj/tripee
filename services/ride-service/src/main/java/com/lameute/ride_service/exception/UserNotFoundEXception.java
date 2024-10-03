package com.lameute.ride_service.exception;

public class UserNotFoundEXception extends RuntimeException{
    public UserNotFoundEXception(String msg){
        super(msg);
    }
}
