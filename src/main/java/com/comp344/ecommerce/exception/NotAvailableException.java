package com.comp344.ecommerce.exception;

/**
 * Created by Byambatsog on 11/1/16.
 */
public class NotAvailableException extends RuntimeException {

    private static final long serialVersionUID = 638990353132197033L;

    private final String errorMessage;

    public NotAvailableException(String message){
        errorMessage = message;
    }

    public String getErrorMessage(){
        return errorMessage;
    }
}
