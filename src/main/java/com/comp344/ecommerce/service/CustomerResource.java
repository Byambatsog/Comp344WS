package com.comp344.ecommerce.service;

import com.comp344.ecommerce.business.CustomerManager;
import com.comp344.ecommerce.domain.CreditCard;
import com.comp344.ecommerce.domain.Customer;
import com.comp344.ecommerce.domain.CustomerAddress;
import com.comp344.ecommerce.domain.Login;
import com.comp344.ecommerce.service.representation.CustomerCreateRequest;
import com.comp344.ecommerce.service.representation.CustomerRepresentation;
import com.comp344.ecommerce.service.representation.CustomerRequest;
import com.comp344.ecommerce.service.representation.Message;
import com.comp344.ecommerce.service.workflow.CustomerActivity;
import com.comp344.ecommerce.utils.Page;
import com.comp344.ecommerce.utils.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Byambatsog on 10/2/16.
 */
@Controller
@RequestMapping("/customerservice")
public class CustomerResource {

    @Autowired
    private CustomerActivity customerActivity;

    @RequestMapping(value = "/customer/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<CustomerRepresentation> getCustomer(@PathVariable(value = "id") Integer id) throws Exception {
        return new ResponseEntity<CustomerRepresentation>(customerActivity.getCustomer(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/customer", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Page<CustomerRepresentation>>  getCustomers(
            @RequestParam(value = "q", required = false) String searchQuery,
            @RequestParam(value = "orderBy", required = false) String orderBy,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) throws Exception {
        return new ResponseEntity<Page<CustomerRepresentation>>(customerActivity.findCustomers(searchQuery, orderBy, page, pageSize), HttpStatus.OK);
    }

    @RequestMapping(value = "/customer", method = RequestMethod.POST, consumes="application/json")
    @ResponseBody
    public ResponseEntity<CustomerRepresentation> createCustomer(@RequestBody CustomerCreateRequest customerCreateRequest) throws Exception {
        return new ResponseEntity<CustomerRepresentation>(customerActivity.createCustomer(customerCreateRequest), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/customer/{id}", method = RequestMethod.PUT, consumes="application/json")
    @ResponseBody
    public ResponseEntity<Message> updateCustomer(@PathVariable(value = "id") Integer id,
                                                 @RequestBody CustomerRequest customerRequest) throws Exception {

        customerActivity.updateCustomer(id, customerRequest);
        return new ResponseEntity<Message>(new Message("Customer updated successfully"), HttpStatus.OK);
    }

    @RequestMapping(value = "/customer/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Message> deleteCustomer(@PathVariable(value = "id") Integer id) throws Exception {

        customerActivity.deleteCustomer(id);
        return new ResponseEntity<Message>(new Message("Customer deleted successfully"), HttpStatus.OK);
    }
}
