package com.lameute.expedition_service.exceptions;

public class UserNotFoundEXception extends RuntimeException{
    public UserNotFoundEXception(String msg){
        super(msg);
    }
}
