package com.ecom.authenticationservice.exceptions;

public class InvalidTokenException extends Exception{

    public InvalidTokenException(String exceptionMessage){
        super(exceptionMessage);
    }
}
