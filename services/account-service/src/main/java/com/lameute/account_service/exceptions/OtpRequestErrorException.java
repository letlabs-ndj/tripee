package com.lameute.account_service.exceptions;

public class OtpRequestErrorException extends RuntimeException{
    public OtpRequestErrorException() {
        super("Problème survenu lors de l'envoi de la requete verifiez les données fournis");
    }
}
