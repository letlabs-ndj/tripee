package com.lameute.account_service.exceptions;

public class EmailAlreadyUsedException extends RuntimeException{
    public EmailAlreadyUsedException() {
        super("Cette email à déja été utilisé");
    }
}
