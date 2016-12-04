package com.comp344.ecommerce.service;

import com.comp344.ecommerce.service.representation.CategoryRepresentation;
import com.comp344.ecommerce.service.workflow.CategoryActivity;
import com.comp344.ecommerce.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Byambatsog on 10/31/16.
 */
@Controller
@RequestMapping("/categoryservice")
public class CategoryResource {

    @Autowired
    private CategoryActivity categoryActivity;

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Page<CategoryRepresentation>> getAllCategories() throws Exception {
        return new ResponseEntity<Page<CategoryRepresentation>>(categoryActivity.getAllCategories(), HttpStatus.OK);
    }

    @RequestMapping(value = "/categories/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<CategoryRepresentation> getCategory(@PathVariable(value = "id") Integer id) throws Exception {
        return new ResponseEntity<CategoryRepresentation>(categoryActivity.getCategory(id), HttpStatus.OK);
    }
}
