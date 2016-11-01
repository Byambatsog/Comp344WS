package com.comp344.ecommerce.service;

import com.comp344.ecommerce.service.representation.OrderCreateRequest;
import com.comp344.ecommerce.service.representation.OrderRepresentation;
import com.comp344.ecommerce.service.workflow.OrderActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
