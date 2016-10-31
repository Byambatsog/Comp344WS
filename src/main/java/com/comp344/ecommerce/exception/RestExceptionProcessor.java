package com.comp344.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Byambatsog on 10/29/16.
 */
@ControllerAdvice
public class RestExceptionProcessor {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorInfo resourceNotFound(HttpServletRequest req, ResourceNotFoundException ex) {
        String errorMessage = ex.getErrorMessage();
        String errorURL = req.getRequestURL().toString();
        return new ErrorInfo(errorURL, errorMessage);
    }

    @ExceptionHandler(LoginRegistrationException.class)
    @ResponseStatus(value= HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorInfo loginRegistration(HttpServletRequest req, LoginRegistrationException ex) {
        String errorMessage = ex.getErrorMessage();
        String errorURL = req.getRequestURL().toString();
        return new ErrorInfo(errorURL, errorMessage);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorInfo internalServerException(HttpServletRequest req, Exception ex) {
        ex.printStackTrace();
        String errorMessage = "internal server error";
        String errorURL = req.getRequestURL().toString();
        return new ErrorInfo(errorURL, errorMessage);
    }

    @ExceptionHandler(NoSuchRequestHandlingMethodException.class)
    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorInfo resourceNotAvailable(HttpServletRequest req, NoSuchRequestHandlingMethodException ex) {
        String errorMessage = "The requested resource is not available";
        String errorURL = req.getRequestURL().toString();
        return new ErrorInfo(errorURL, errorMessage);
    }
}
