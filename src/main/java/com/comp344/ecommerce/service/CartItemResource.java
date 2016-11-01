package com.comp344.ecommerce.service;

import com.comp344.ecommerce.service.representation.CartItemRepresentation;
import com.comp344.ecommerce.service.representation.CartItemRequest;
import com.comp344.ecommerce.service.representation.Message;
import com.comp344.ecommerce.service.workflow.CartItemActivity;
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
@RequestMapping("/customerservice")
public class CartItemResource {

    @Autowired
    private CartItemActivity cartItemActivity;

    @RequestMapping(value = "/customer/{id}/cartitem", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<CartItemRepresentation>> getAllCartItems(@PathVariable(value = "id") Integer customerId) throws Exception {
        return new ResponseEntity<List<CartItemRepresentation>>(cartItemActivity.getAllCartItems(customerId), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/customer/{id}/cartitem", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<CartItemRepresentation> createCartItem(@PathVariable(value = "id") Integer customerId,
                                                                     @RequestBody CartItemRequest cartItemRequest) throws Exception {

        return new ResponseEntity<CartItemRepresentation>(cartItemActivity.addCartItem(customerId, cartItemRequest), HttpStatus.OK);
    }

    @RequestMapping(value = "/customer/{id}/cartitem/{cartItemId}", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Message> updateCartItem(@PathVariable(value = "id") Integer customerId,
                                                 @PathVariable(value = "cartItemId") Integer cartItemId,
                                                 @RequestBody CartItemRequest cartItemRequest) throws Exception {
        cartItemActivity.updateCartItem(cartItemId, cartItemRequest);
        return new ResponseEntity<Message>(new Message("Cart item updated successfully"), HttpStatus.OK);
    }

    @RequestMapping(value = "/customer/{id}/cartitem/{cartItemId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<CartItemRepresentation> getCartItem(@PathVariable(value = "id") Integer customerId,
                                                               @PathVariable(value = "cartItemId") Integer cartItemId) throws Exception {
        return new ResponseEntity<CartItemRepresentation>(cartItemActivity.getCartItem(cartItemId, customerId), HttpStatus.OK);
    }

    @RequestMapping(value = "/customer/{id}/cartitem/{cartItemId}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<Message> deleteCartItem(@PathVariable(value = "id") Integer customerId,
                                                 @PathVariable(value = "cartItemId") Integer cartItemId) throws Exception {

        cartItemActivity.deleteCartItem(cartItemId, customerId);
        return new ResponseEntity<Message>(new Message("Cart item deleted successfully"), HttpStatus.OK);
    }

}
