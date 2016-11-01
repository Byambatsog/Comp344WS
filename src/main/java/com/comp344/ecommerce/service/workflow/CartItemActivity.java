package com.comp344.ecommerce.service.workflow;

import com.comp344.ecommerce.business.CartManager;
import com.comp344.ecommerce.business.CustomerManager;
import com.comp344.ecommerce.business.ProductManager;
import com.comp344.ecommerce.domain.Cart;
import com.comp344.ecommerce.domain.CartItem;
import com.comp344.ecommerce.exception.ResourceNotFoundException;
import com.comp344.ecommerce.service.representation.CartItemRepresentation;
import com.comp344.ecommerce.service.representation.CartItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Byambatsog on 10/31/16.
 */
@Component
public class CartItemActivity {

    @Autowired
    private CartManager cartManager;

    @Autowired
    private CustomerManager customerManager;

    @Autowired
    private ProductManager productManager;

    public List<CartItemRepresentation> getAllCartItems(Integer customerId) throws Exception{

        List<CartItemRepresentation> cartItemRepresentations = new ArrayList<CartItemRepresentation>();
        Cart cart = cartManager.findActiveCart(customerId);

        if(cart != null){
            List<CartItem> list = cartManager.findItem(cart.getId());
            for(CartItem item : list){
                cartItemRepresentations.add(new CartItemRepresentation(item));
            }
        }

        return cartItemRepresentations;
    }

    public CartItemRepresentation addCartItem(Integer customerId, CartItemRequest itemRequest) throws Exception{

        Cart cart = cartManager.findActiveCart(customerId);

        if(cart == null){
            cart = new Cart();
            cart.setCreatedAt(new Date());
            cart.setCustomer(customerManager.get(customerId));
            cart.setStatus(Boolean.TRUE);
            cartManager.save(cart);
        }

        List<CartItem> cartItems = cartManager.findItem(cart.getId());
        boolean exist = false;
        Integer cartItemId = 0;
        for(CartItem cartItem : cartItems){
            if(cartItem.getProduct().getId().equals(itemRequest.getProductId())){
                exist = true;
                cartItemId = cartItem.getId();
                break;
            }
        }

        CartItem cartItem;

        if(!exist){
            cartItem = new CartItem();
            cartItem.setCustomerCart(cart);
            cartItem.setProduct(productManager.get(itemRequest.getProductId()));
            cartItem.setQuantity(itemRequest.getQuantity());
        } else {
            cartItem = cartManager.getItem(cartItemId);
            cartItem.setQuantity(cartItem.getQuantity() + itemRequest.getQuantity());
        }
        cartManager.saveItem(cartItem);
        return new CartItemRepresentation(cartItem);
    }

    public void updateCartItem(Integer id, CartItemRequest itemRequest) throws Exception{
        CartItem cartItem = cartManager.getItem(id);
        cartItem.setQuantity(itemRequest.getQuantity());
        cartManager.saveItem(cartItem);
    }

    public CartItemRepresentation getCartItem(Integer id, Integer customerId) throws Exception {
        CartItem cartItem = cartManager.getItem(id);
        if(cartItem == null) {
            throw new ResourceNotFoundException("No cart item found with id " + id);
        } else if (!cartItem.getCustomerCart().getCustomer().getId().equals(customerId)){
            throw new ResourceNotFoundException("No cart item found with id " + id + " for customer with id " + customerId);
        }
        return new CartItemRepresentation(cartItem);
    }

    public void deleteCartItem(Integer id, Integer customerId) throws Exception{
        CartItem cartItem = cartManager.getItem(id);
        if(cartItem == null) {
            throw new ResourceNotFoundException("No cart item found with id " + id);
        } else if (!cartItem.getCustomerCart().getCustomer().getId().equals(customerId)){
            throw new ResourceNotFoundException("No cart item found with id " + id + " for customer with id " + customerId);
        }
        cartManager.deleteItem(id);
    }

}
