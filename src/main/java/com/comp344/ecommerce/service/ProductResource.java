package com.comp344.ecommerce.service;

import java.util.List;

import com.comp344.ecommerce.service.representation.*;
import com.comp344.ecommerce.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.comp344.ecommerce.service.workflow.ProductActivity;

@Controller
@RequestMapping("/productservice")
public class ProductResource {

	@Autowired
    private ProductActivity productActivity;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
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

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ProductDetailRepresentation> getProduct(@PathVariable(value = "id") Integer id) throws Exception {
        return new ResponseEntity<ProductDetailRepresentation>(productActivity.getProduct(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/products/{id}/reviews", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<ReviewRepresentation>> getAllReviews(@PathVariable(value = "id") Integer productId) throws Exception {
        return new ResponseEntity<List<ReviewRepresentation>>(productActivity.getAllReviews(productId), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/products/{id}/reviews", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<ReviewRepresentation> createReview(@PathVariable(value = "id") Integer productId,
                                                             @RequestBody ReviewRequest reviewRequest) throws Exception {

        return new ResponseEntity<ReviewRepresentation>(productActivity.createReview(productId, reviewRequest), HttpStatus.OK);
    }

    @RequestMapping(value = "/reviews/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ReviewRepresentation> getReview(@PathVariable(value = "id") Integer id) throws Exception {
        return new ResponseEntity<ReviewRepresentation>(productActivity.getReview(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/reviews/{id}", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Message> updateReview(@PathVariable(value = "id") Integer id,
                                                @RequestBody ReviewRequest reviewRequest) throws Exception {
        productActivity.updateReview(id, reviewRequest);
        return new ResponseEntity<Message>(new Message("Review updated successfully"), HttpStatus.OK);
    }

    @RequestMapping(value = "/reviews/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Message> deleteReview(@PathVariable(value = "id") Integer id) throws Exception {
        productActivity.deleteReview(id);
        return new ResponseEntity<Message>(new Message("Review deleted successfully"), HttpStatus.OK);
    }

    @RequestMapping(value = "/products/{id}/partners", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ProductPartnerRepresentation> getPartner(@PathVariable(value = "id") Integer productId) throws Exception {
        return new ResponseEntity<ProductPartnerRepresentation>(productActivity.getPartners(productId), HttpStatus.OK);
    }
    
}
