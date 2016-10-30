package com.comp344.ecommerce.exception;

/**
 * Created by Byambatsog on 10/29/16.
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 7840656825338736534L;
    private final String errorMessage;

    public ResourceNotFoundException(String message){
        errorMessage = message;
    }

    public String getErrorMessage(){
        return errorMessage;
    }
}
