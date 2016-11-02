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

    @Autowired
    private OrderActivity orderActivity;

    @RequestMapping(value = "/partner/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<PartnerRepresentation>  getPartner(@PathVariable(value = "id") Integer id) throws Exception {
        return new ResponseEntity<PartnerRepresentation>(partnerActivity.getPartner(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/partner", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Page<PartnerRepresentation>>  getPartners(
            @RequestParam(value = "q", required = false) String searchQuery,
            @RequestParam(value = "type", required = false) PartnerType type,
            @RequestParam(value = "orderBy", required = false) String orderBy,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) throws Exception {
        return new ResponseEntity<Page<PartnerRepresentation>>(partnerActivity.findPartners(searchQuery, type, orderBy, page, pageSize), HttpStatus.OK);
    }

    @RequestMapping(value = "/partner", method = RequestMethod.POST, consumes="application/json")
    @ResponseBody
    public ResponseEntity<PartnerRepresentation> createPartner(@RequestBody PartnerCreateRequest partnerCreateRequest) throws Exception {
        return new ResponseEntity<PartnerRepresentation>(partnerActivity.createPartner(partnerCreateRequest), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/partner/{id}", method = RequestMethod.PUT, consumes="application/json")
    @ResponseBody
    public ResponseEntity<Message> updatePartner(@PathVariable(value = "id") Integer id,
                                  @RequestBody PartnerRequest partnerRequest) throws Exception {

        partnerActivity.updatePartner(id, partnerRequest);
        return new ResponseEntity<Message>(new Message("Partner updated successfully"), HttpStatus.OK);
    }

    @RequestMapping(value = "/partner/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Message> deletePartner(@PathVariable(value = "id") Integer id) throws Exception {

        partnerActivity.deletePartner(id);
        return new ResponseEntity<Message>(new Message("Partner deleted successfully"), HttpStatus.OK);
    }

    @RequestMapping(value = "/partner/{id}/product", method = RequestMethod.GET)
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

    @RequestMapping(value = "/partner/{id}/product", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<PartnerProductRepresentation> createProduct(@PathVariable(value = "id") Integer partnerId,
                                               @RequestBody ProductRequest productRequest) throws Exception {

        return new ResponseEntity<PartnerProductRepresentation>(partnerActivity.createProduct(partnerId, productRequest), HttpStatus.OK);
    }

    @RequestMapping(value = "/partner/{id}/product/{productId}", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Message> updateProduct(@PathVariable(value = "id") Integer partnerId,
                                                 @PathVariable(value = "productId") Integer productId,
                                                  @RequestBody ProductRequest productRequest) throws Exception {
        partnerActivity.updateProduct(productId, productRequest);
        return new ResponseEntity<Message>(new Message("Product updated successfully"), HttpStatus.OK);
    }

    @RequestMapping(value = "/partner/{id}/product/{productId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<PartnerProductRepresentation> getProduct(@PathVariable(value = "id") Integer partnerId,
                                                 @PathVariable(value = "productId") Integer productId) throws Exception {
        return new ResponseEntity<PartnerProductRepresentation>(partnerActivity.getProduct(productId, partnerId), HttpStatus.OK);
    }

    @RequestMapping(value = "/partner/{id}/product/{productId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Message> deleteProduct(@PathVariable(value = "id") Integer partnerId,
                                                 @PathVariable(value = "productId") Integer productId) throws Exception {

        partnerActivity.deleteProduct(productId, partnerId);
        return new ResponseEntity<Message>(new Message("Product deleted successfully"), HttpStatus.OK);
    }

    @RequestMapping(value = "/partner/{id}/order", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<OrderRepresentation>> getAllOrders(@PathVariable(value = "id") Integer partnerId,
                                                                  @RequestParam(value = "status", required = false) OrderProductStatus status) throws Exception {
        return new ResponseEntity<List<OrderRepresentation>>(orderActivity.getPartnerOrders(partnerId, status), HttpStatus.CREATED);
    }


    @RequestMapping(value = "/partner/{id}/order/{orderId}/product/{productId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Message> fulfillOrder(@PathVariable(value = "id") Integer partnerId,
                                                @PathVariable(value = "orderId") Integer orderId,
                                                @PathVariable(value = "productId") Integer productId,
                                                @RequestParam(value = "trackingNumber", required = true) String trackingNumber) throws Exception {
        orderActivity.fulfillOrderProduct(partnerId, orderId, productId, trackingNumber);

        return new ResponseEntity<Message>(new Message("Order product is fulfilled successfully"), HttpStatus.OK);
    }

    @RequestMapping(value = "/partner/{id}/order/{orderId}/product/{productId}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Message> updateOrderProductStatus(@PathVariable(value = "id") Integer partnerId,
                                                @PathVariable(value = "orderId") Integer orderId,
                                                @PathVariable(value = "productId") Integer productId,
                                                @RequestParam(value = "status", required = true) OrderProductStatus status) throws Exception {

        if(status.equals(OrderProductStatus.SHIPPED)){
            orderActivity.shipOrderProduct(partnerId, orderId, productId);
        } else if(status.equals(OrderProductStatus.DELIVERED)){
            orderActivity.setOrderProductStatusDelivered(partnerId, orderId, productId);
        }
        return new ResponseEntity<Message>(new Message("Order product's status is changed successfully to '" + status.name() + "'"), HttpStatus.OK);
    }
}