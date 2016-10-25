package com.comp344.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.comp344.ecommerce.service.representation.ProductRepresentation;
import com.comp344.ecommerce.service.representation.ProductRequest;
import com.comp344.ecommerce.service.workflow.ProductActivity;

@Controller
@RequestMapping("/productservice")
public class ProductResource {

	@Autowired
    private ProductActivity productActivity;

    @RequestMapping(value="/product", method = RequestMethod.POST, consumes="application/json")
    @ResponseBody
    public ProductRepresentation create(@RequestBody ProductRequest productRequest) throws Exception {
    	return productActivity.createProduct(productRequest);
    }
    
    @RequestMapping(value="/product", method = RequestMethod.GET)
    @ResponseBody
    public List<ProductRepresentation> getProducts(@RequestParam(value = "search", required = true) String search,
    		@RequestParam(value = "page", required = false, defaultValue = "1") int page,
    		@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) throws Exception {
    	return productActivity.searchProduct(search, page, pageSize);
    }
}
