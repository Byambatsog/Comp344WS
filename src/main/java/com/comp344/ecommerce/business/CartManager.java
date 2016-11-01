package com.comp344.ecommerce.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comp344.ecommerce.dao.HibernateCartItemRepository;
import com.comp344.ecommerce.dao.HibernateCartRepository;
import com.comp344.ecommerce.domain.Cart;
import com.comp344.ecommerce.domain.CartItem;


@Service("cartService")
public class CartManager {
	
	@Autowired
    private HibernateCartRepository cartRepository;
	
	@Autowired
    private HibernateCartItemRepository cartItemRepository;

	public void save(Cart cart) throws Exception {
        cartRepository.save(cart);
    }

    public Cart get(Integer id) throws Exception {
        return cartRepository.get(id);
    }

    public void delete(Integer id) throws Exception {
        Cart cart = cartRepository.get(id);
        cartRepository.delete(cart);
    }

    public Cart findActiveCart(Integer customerId){
        List<Cart> list = cartRepository.findByCustomer(customerId, Boolean.TRUE);
        if(list != null && list.size()>0) return list.get(0);
        else return null;
    }
    
    public void saveItem(CartItem cartItem) throws Exception {
        cartItemRepository.save(cartItem);
    }

    public CartItem getItem(Integer id) throws Exception {
        return cartItemRepository.get(id);
    }

    public void deleteItem(Integer id) throws Exception {
        CartItem cartItem = cartItemRepository.get(id);
        cartItemRepository.delete(cartItem);
    }

    public List<CartItem> findItem(Integer cartId){
        return cartItemRepository.findByCart(cartId);
    }
}
