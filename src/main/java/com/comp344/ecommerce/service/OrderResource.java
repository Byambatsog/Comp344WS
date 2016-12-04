package com.comp344.ecommerce.service;

import com.comp344.ecommerce.service.representation.*;
import com.comp344.ecommerce.service.workflow.OrderActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Byambatsog on 10/31/16.
 */
@Controller
@RequestMapping("/orderservice")
public class OrderResource {

    @Autowired
    private OrderActivity orderActivity;

    @RequestMapping(value = "/order", method = RequestMethod.POST, consumes="application/json")
    @ResponseBody
    public ResponseEntity<OrderRepresentation> createOrder(@RequestBody OrderCreateRequest orderCreateRequest) throws Exception {
        return new ResponseEntity<OrderRepresentation>(orderActivity.createOrder(orderCreateRequest), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<OrderRepresentation> getOrder(@PathVariable(value = "id") Integer id) throws Exception {
        return new ResponseEntity<OrderRepresentation>(orderActivity.getOrder(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/order/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Message> cancelOrder(@PathVariable(value = "id") Integer id) throws Exception {

        orderActivity.cancelOrder(id);
        return new ResponseEntity<Message>(new Message("Order cancelled successfully"), HttpStatus.OK);
    }

    @RequestMapping(value = "/order/{id}/status", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<OrderStatusRepresentation>> getOrderStatuses(@PathVariable(value = "id") Integer orderId) throws Exception {
        return new ResponseEntity<List<OrderStatusRepresentation>>(orderActivity.getOrderStatuses(orderId), HttpStatus.OK);
    }

    @RequestMapping(value = "/order/{id}/product", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<OrderProductRepresentation>> getOrderProducts(@PathVariable(value = "id") Integer orderId) throws Exception {
        return new ResponseEntity<List<OrderProductRepresentation>>(orderActivity.getOrderProducts(orderId), HttpStatus.OK);
    }

}
