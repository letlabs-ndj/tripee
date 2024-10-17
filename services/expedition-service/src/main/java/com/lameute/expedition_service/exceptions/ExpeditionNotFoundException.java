package com.lameute.expedition_service.exceptions;

public class ExpeditionNotFoundException extends RuntimeException{
    public ExpeditionNotFoundException(String msg){
        super(msg);
    }
}
