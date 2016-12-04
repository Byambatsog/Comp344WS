package com.comp344.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by Byambatsog on 10/30/16.
 */
@Controller
@RequestMapping("/")
public class DefaultResource {

    @Autowired
    ServletContext servletContext;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String getDocumentation() throws Exception {
        return "Welcome to E-commerce";
    }
}
