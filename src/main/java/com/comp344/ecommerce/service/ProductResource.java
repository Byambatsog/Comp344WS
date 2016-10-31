package com.comp344.ecommerce.service;

import java.util.List;

import com.comp344.ecommerce.service.representation.ProductDetailRepresentation;
import com.comp344.ecommerce.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.comp344.ecommerce.service.representation.ProductRepresentation;
import com.comp344.ecommerce.service.representation.ProductRequest;
import com.comp344.ecommerce.service.workflow.ProductActivity;

@Controller
@RequestMapping("/productservice")
public class ProductResource {

	@Autowired
    private ProductActivity productActivity;

    @RequestMapping(value = "/product", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Page<ProductRepresentation>> getProducts(
            @RequestParam(value = "q", required = false) String searchQuery,
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "partnerId", required = false) Integer partnerId,
            @RequestParam(value = "orderBy", required = false) String orderBy,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) throws Exception {
        return new ResponseEntity<Page<ProductRepresentation>>(productActivity.findProducts(searchQuery, categoryId, partnerId, orderBy, page, pageSize), HttpStatus.OK);
    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ProductDetailRepresentation> getProduct(@PathVariable(value = "id") Integer id) throws Exception {
        return new ResponseEntity<ProductDetailRepresentation>(productActivity.getProduct(id), HttpStatus.OK);
    }
}
