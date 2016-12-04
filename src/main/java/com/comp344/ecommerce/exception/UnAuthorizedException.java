package com.comp344.ecommerce.exception;

/**
 * Created by Byambatsog on 12/2/16.
 */
public class UnAuthorizedException extends RuntimeException {

    private static final long serialVersionUID = -4636899514575748889L;
    private final String errorMessage;

    public UnAuthorizedException(String message){
        errorMessage = message;
    }

    public String getErrorMessage(){
        return errorMessage;
    }
}
