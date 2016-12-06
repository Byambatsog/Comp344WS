package com.comp344.ecommerce.jwt;

import com.comp344.ecommerce.exception.ErrorInfo;
import com.comp344.ecommerce.service.representation.BaseRepresentation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Byambatsog on 11/27/16.
 */
public class StatelessAuthenticationFilter extends GenericFilterBean {

    public static final String CREDENTIALS_NAME = "Access-Control-Allow-Credentials";
    public static final String ORIGIN_NAME = "Access-Control-Allow-Origin";
    public static final String METHODS_NAME = "Access-Control-Allow-Methods";
    public static final String HEADERS_NAME = "Access-Control-Allow-Headers";
    public static final String MAX_AGE_NAME = "Access-Control-Max-Age";

    private final TokenAuthenticationService authenticationService;

    public StatelessAuthenticationFilter(TokenAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader(CREDENTIALS_NAME, "true");
        httpServletResponse.setHeader(ORIGIN_NAME, "*");
        httpServletResponse.setHeader(METHODS_NAME, "GET, POST, PUT, DELETE, OPTIONS, ");
        httpServletResponse.setHeader(HEADERS_NAME, "Origin, X-Requested-With, X-AUTH-TOKEN, Content-Type, Accept");
        httpServletResponse.setHeader(MAX_AGE_NAME, "3600");

        try{
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            Authentication authentication = authenticationService.getAuthentication(httpRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
            SecurityContextHolder.getContext().setAuthentication(null);

        } catch (ExpiredJwtException e) {

            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpServletResponse.setContentType("application/json");
            String errorMessage = "Authentication token is expired. Please log in again!";
            String errorURL = BaseRepresentation.BASE_URI + "/login";
            ErrorInfo errorInfo = new ErrorInfo(errorURL, errorMessage);
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            httpServletResponse.getWriter().print(ow.writeValueAsString(errorInfo));
        }

    }
}
