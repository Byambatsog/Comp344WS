package com.comp344.ecommerce.exception;

import com.comp344.ecommerce.service.representation.BaseRepresentation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Byambatsog on 11/28/16.
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        String errorMessage = "Authentication is required";
        String errorURL = BaseRepresentation.BASE_URI + "/login";
        ErrorInfo errorInfo = new ErrorInfo(errorURL, errorMessage);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        response.getWriter().print(ow.writeValueAsString(errorInfo));
    }
}
