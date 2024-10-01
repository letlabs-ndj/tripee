package com.lameute.account_service.exceptions;

public class OtpNotSentException extends RuntimeException{
    public OtpNotSentException() {
        super("Problème lors de l'envoi deu code otp veillez reessayer");
    }
}
