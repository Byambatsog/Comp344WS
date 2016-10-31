package com.comp344.ecommerce.exception;

/**
 * Created by Byambatsog on 10/30/16.
 */
public class LoginRegistrationException extends RuntimeException {

    private static final long serialVersionUID = -657208713742127575L;

    private final String errorMessage;

    public LoginRegistrationException(String message){
        errorMessage = message;
    }

    public String getErrorMessage(){
        return errorMessage;
    }
}
