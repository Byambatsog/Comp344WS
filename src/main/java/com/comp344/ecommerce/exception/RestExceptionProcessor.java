package com.comp344.ecommerce.exception;

import com.comp344.ecommerce.service.representation.BaseRepresentation;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
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
    public ErrorInfo handleResourceNotFoundException(HttpServletRequest req, ResourceNotFoundException ex) {
        String errorMessage = ex.getErrorMessage();
        String errorURL = req.getRequestURL().toString();
        return new ErrorInfo(errorURL, errorMessage);
    }

    @ExceptionHandler(LoginRegistrationException.class)
    @ResponseStatus(value= HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorInfo handleLoginRegistrationException(HttpServletRequest req, LoginRegistrationException ex) {
        String errorMessage = ex.getErrorMessage();
        String errorURL = req.getRequestURL().toString();
        return new ErrorInfo(errorURL, errorMessage);
    }

    @ExceptionHandler(NotAvailableException.class)
    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorInfo handleNotAvailableException(HttpServletRequest req, NotAvailableException ex) {
        String errorMessage = ex.getErrorMessage();
        String errorURL = req.getRequestURL().toString();
        return new ErrorInfo(errorURL, errorMessage);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(value= HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorInfo handleUnAuthorizedException(HttpServletRequest req, UnAuthorizedException ex) {
        String errorMessage = ex.getErrorMessage();
        String errorURL = req.getRequestURL().toString();
        return new ErrorInfo(errorURL, errorMessage);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(value= HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorInfo handleBadCredentialsException(HttpServletRequest req, BadCredentialsException ex) {
        String errorMessage = ex.getMessage();
        String errorURL = req.getRequestURL().toString();
        return new ErrorInfo(errorURL, errorMessage);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorInfo handleInternalServerException(HttpServletRequest req, Exception ex) {
        ex.printStackTrace();
        String errorMessage = "internal server error";
        String errorURL = req.getRequestURL().toString();
        return new ErrorInfo(errorURL, errorMessage);
    }
}
