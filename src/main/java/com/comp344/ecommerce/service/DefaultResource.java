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

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getDocumentation() throws Exception {
        String jsonContent = "";
        File file = new File(servletContext.getRealPath("/WEB-INF/classes/documentation.json"));
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            jsonContent = jsonContent + line + " ";
        }
        return jsonContent;
    }
}
