package com.lameute.reservation_service.exceptions;

public class UserNotFoundEXception extends RuntimeException{
    public UserNotFoundEXception(String msg){
        super(msg);
    }
}
