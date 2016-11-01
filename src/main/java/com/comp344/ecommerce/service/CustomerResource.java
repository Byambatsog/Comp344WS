package com.comp344.ecommerce.service;

import com.comp344.ecommerce.service.representation.*;
import com.comp344.ecommerce.service.workflow.CustomerActivity;
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

    @RequestMapping(value = "/customer/{id}/creditcard", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<CreditCardRepresentation>> getAllCreditCards(@PathVariable(value = "id") Integer customerId) throws Exception {
        return new ResponseEntity<List<CreditCardRepresentation>>(customerActivity.getAllCreditCards(customerId), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/customer/{id}/creditcard", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<CreditCardRepresentation> createCreditCard(@PathVariable(value = "id") Integer customerId,
                                                                      @RequestBody CreditCardRequest cardRequest) throws Exception {

        return new ResponseEntity<CreditCardRepresentation>(customerActivity.createCreditCard(customerId, cardRequest), HttpStatus.OK);
    }

    @RequestMapping(value = "/customer/{id}/creditcard/{cardId}", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Message> updateCreditCard(@PathVariable(value = "id") Integer customerId,
                                                 @PathVariable(value = "cardId") Integer cardId,
                                                 @RequestBody CreditCardRequest cardRequest) throws Exception {
        customerActivity.updateCreditCard(cardId, cardRequest);
        return new ResponseEntity<Message>(new Message("Credit card updated successfully"), HttpStatus.OK);
    }

    @RequestMapping(value = "/customer/{id}/creditcard/{cardId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<CreditCardRepresentation> getCreditCard(@PathVariable(value = "id") Integer customerId,
                                                               @PathVariable(value = "cardId") Integer cardId) throws Exception {
        return new ResponseEntity<CreditCardRepresentation>(customerActivity.getCreditCard(cardId, customerId), HttpStatus.OK);
    }

    @RequestMapping(value = "/customer/{id}/creditcard/{cardId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Message> deleteCreditCard(@PathVariable(value = "id") Integer customerId,
                                                 @PathVariable(value = "cardId") Integer cardId) throws Exception {

        customerActivity.deleteCreditCard(cardId, customerId);
        return new ResponseEntity<Message>(new Message("Credit card deleted successfully"), HttpStatus.OK);
    }

    @RequestMapping(value = "/customer/{id}/address", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<AddressRepresentation>> getAllAddresses(@PathVariable(value = "id") Integer customerId) throws Exception {
        return new ResponseEntity<List<AddressRepresentation>>(customerActivity.getAllAddresses(customerId), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/customer/{id}/address", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<AddressRepresentation> createAddresses(@PathVariable(value = "id") Integer customerId,
                                                                     @RequestBody AddressRequest addressRequest) throws Exception {

        return new ResponseEntity<AddressRepresentation>(customerActivity.createAddress(customerId, addressRequest), HttpStatus.OK);
    }

    @RequestMapping(value = "/customer/{id}/address/{addressId}", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Message> updateAddress(@PathVariable(value = "id") Integer customerId,
                                                @PathVariable(value = "addressId") Integer addressId,
                                                @RequestBody AddressRequest addressRequest) throws Exception {
        customerActivity.updateAddress(addressId, addressRequest);
        return new ResponseEntity<Message>(new Message("Address updated successfully"), HttpStatus.OK);
    }

    @RequestMapping(value = "/customer/{id}/address/{addressId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<AddressRepresentation> getAddress(@PathVariable(value = "id") Integer customerId,
                                                          @PathVariable(value = "addressId") Integer addressId) throws Exception {
        return new ResponseEntity<AddressRepresentation>(customerActivity.getAddress(addressId, customerId), HttpStatus.OK);
    }

    @RequestMapping(value = "/customer/{id}/address/{addressId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Message> deleteAddress(@PathVariable(value = "id") Integer customerId,
                                                @PathVariable(value = "addressId") Integer addressId) throws Exception {

        customerActivity.deleteAddress(addressId, customerId);
        return new ResponseEntity<Message>(new Message("Address deleted successfully"), HttpStatus.OK);
    }
    
}
