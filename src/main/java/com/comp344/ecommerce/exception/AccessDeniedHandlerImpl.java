package com.comp344.ecommerce.exception;

import com.comp344.ecommerce.service.representation.BaseRepresentation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Byambatsog on 11/28/16.
 */
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        String errorMessage = "Unauthorized";
        String errorURL = BaseRepresentation.BASE_URI + "/login";
        ErrorInfo errorInfo = new ErrorInfo(errorURL, errorMessage);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        response.getWriter().print(ow.writeValueAsString(errorInfo));
    }
}
