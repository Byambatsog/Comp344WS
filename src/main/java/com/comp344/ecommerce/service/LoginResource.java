package com.comp344.ecommerce.service;

import com.comp344.ecommerce.service.representation.LoginRepresentation;
import com.comp344.ecommerce.service.workflow.LoginActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Byambatsog on 11/27/16.
 */
@Controller
@RequestMapping("/")
public class LoginResource {

    @Autowired
    private LoginActivity loginActivity;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<LoginRepresentation> login(@RequestParam(value = "username", required = true) String username,
                                                     @RequestParam(value = "password", required = true) String password,
                                                     HttpServletResponse response) throws Exception {
        return new ResponseEntity<LoginRepresentation>(loginActivity.login(username, password, response), HttpStatus.OK);
    }
}
