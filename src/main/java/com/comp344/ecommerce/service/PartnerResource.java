package com.comp344.ecommerce.service;

import com.comp344.ecommerce.domain.OrderProductStatus;
import com.comp344.ecommerce.domain.PartnerType;
import com.comp344.ecommerce.service.representation.*;
import com.comp344.ecommerce.service.workflow.OrderActivity;
import com.comp344.ecommerce.service.workflow.PartnerActivity;
import com.comp344.ecommerce.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Created by Byambatsog on 10/2/16.
 */
@Controller
@RequestMapping("/partnerservice")
public class PartnerResource {

    @Autowired
    private PartnerActivity partnerActivity;

    @RequestMapping(value = "/partners", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Page<PartnerRepresentation>>  getPartners(
            @RequestParam(value = "q", required = false) String searchQuery,
            @RequestParam(value = "type", required = false) PartnerType type,
            @RequestParam(value = "orderBy", required = false) String orderBy,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) throws Exception {
        return new ResponseEntity<Page<PartnerRepresentation>>(partnerActivity.findPartners(searchQuery, type, orderBy, page, pageSize), HttpStatus.OK);
    }

    @RequestMapping(value = "/partners", method = RequestMethod.POST, consumes="application/json")
    @ResponseBody
    public ResponseEntity<PartnerRepresentation> createPartner(@RequestBody PartnerCreateRequest partnerCreateRequest) throws Exception {
        return new ResponseEntity<PartnerRepresentation>(partnerActivity.createPartner(partnerCreateRequest), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/partners/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<PartnerRepresentation>  getPartner(@PathVariable(value = "id") Integer id) throws Exception {
        return new ResponseEntity<PartnerRepresentation>(partnerActivity.getPartner(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/partners/{id}", method = RequestMethod.PUT, consumes="application/json")
    @ResponseBody
    public ResponseEntity<Message> updatePartner(@PathVariable(value = "id") Integer id,
                                  @RequestBody PartnerRequest partnerRequest) throws Exception {

        partnerActivity.updatePartner(id, partnerRequest);
        return new ResponseEntity<Message>(new Message("Partner updated successfully"), HttpStatus.OK);
    }

    @RequestMapping(value = "/partners/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Message> deletePartner(@PathVariable(value = "id") Integer id) throws Exception {

        partnerActivity.deletePartner(id);
        return new ResponseEntity<Message>(new Message("Partner deleted successfully"), HttpStatus.OK);
    }

    @RequestMapping(value = "/partners/{id}/products", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Page<PartnerProductRepresentation>> getPartners(@PathVariable(value = "id") Integer partnerId,
                                       @RequestParam(value = "q", required = false) String searchQuery,
                                       @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                       @RequestParam(value = "status", required = false) Boolean status,
                                       @RequestParam(value = "orderBy", required = false) String orderBy,
                                       @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) throws Exception {
        return new ResponseEntity<Page<PartnerProductRepresentation>>(partnerActivity.findProducts(searchQuery, categoryId, partnerId, status, orderBy, page, pageSize), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/partners/{id}/products", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<PartnerProductRepresentation> createProduct(@PathVariable(value = "id") Integer partnerId,
                                               @RequestBody ProductRequest productRequest) throws Exception {

        return new ResponseEntity<PartnerProductRepresentation>(partnerActivity.createProduct(partnerId, productRequest), HttpStatus.OK);
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Message> updateProduct(@PathVariable(value = "id") Integer id,
                                                 @RequestBody ProductRequest productRequest) throws Exception {
        partnerActivity.updateProduct(id, productRequest);
        return new ResponseEntity<Message>(new Message("Product updated successfully"), HttpStatus.OK);
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<PartnerProductRepresentation> getProduct(@PathVariable(value = "id") Integer id) throws Exception {
        return new ResponseEntity<PartnerProductRepresentation>(partnerActivity.getProduct(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Message> deleteProduct(@PathVariable(value = "id") Integer id) throws Exception {

        partnerActivity.deleteProduct(id);
        return new ResponseEntity<Message>(new Message("Product deleted successfully"), HttpStatus.OK);
    }

    @RequestMapping(value = "/partners/{id}/orders", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<OrderRepresentation>> getAllOrders(@PathVariable(value = "id") Integer partnerId,
                                                                  @RequestParam(value = "status", required = false) OrderProductStatus status) throws Exception {
        return new ResponseEntity<List<OrderRepresentation>>(partnerActivity.getPartnerOrders(partnerId, status), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/orders/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<OrderRepresentation> getOrder(@PathVariable(value = "id") Integer id) throws Exception {
        return new ResponseEntity<OrderRepresentation>(partnerActivity.getPartnerOrder(id), HttpStatus.OK);
    }


    @RequestMapping(value = "/orders/{orderId}/products/{productId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Message> fulfillOrder(@PathVariable(value = "orderId") Integer orderId,
                                                @PathVariable(value = "productId") Integer productId,
                                                @RequestParam(value = "trackingNumber", required = true) String trackingNumber) throws Exception {
        partnerActivity.fulfillOrderProduct(orderId, productId, trackingNumber);

        return new ResponseEntity<Message>(new Message("Order product is fulfilled successfully"), HttpStatus.OK);
    }

    @RequestMapping(value = "/orders/{orderId}/products/{productId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Message> updateOrderProductStatus(@PathVariable(value = "orderId") Integer orderId,
                                                @PathVariable(value = "productId") Integer productId,
                                                @RequestParam(value = "status", required = true) OrderProductStatus status) throws Exception {

        if(status.equals(OrderProductStatus.SHIPPED)){
            partnerActivity.shipOrderProduct(orderId, productId);
        } else if(status.equals(OrderProductStatus.DELIVERED)){
            partnerActivity.setOrderProductStatusDelivered(orderId, productId);
        }
        return new ResponseEntity<Message>(new Message("Order product's status is changed successfully to '" + status.name() + "'"), HttpStatus.OK);
    }
}