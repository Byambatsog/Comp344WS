package com.comp344.ecommerce.service.workflow;

import com.comp344.ecommerce.business.*;
import com.comp344.ecommerce.domain.*;
import com.comp344.ecommerce.exception.ResourceNotFoundException;
import com.comp344.ecommerce.service.representation.OrderCreateRequest;
import com.comp344.ecommerce.service.representation.OrderRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Byambatsog on 10/31/16.
 */
@Component
public class OrderActivity {

    @Autowired
    private OrderManager orderManager;

    @Autowired
    private CustomerManager customerManager;

    @Autowired
    private PartnerManager partnerManager;

    @Autowired
    private ProductManager productManager;

    @Autowired
    private CartManager cartManager;

    public OrderRepresentation createOrder(OrderCreateRequest orderCreateRequest) throws Exception {

        Cart cart = cartManager.findActiveCart(orderCreateRequest.getCustomerId());
        if(cart == null)
            throw new ResourceNotFoundException("Your cart is empty! Please add your product into your cart.");

        List<CartItem> cartItems = cartManager.findItem(cart.getId());
        if(cartItems == null || cartItems.size()==0)
            throw new ResourceNotFoundException("Your cart is empty! Please add your product into your cart.");

        Order order = new Order();
        order.setCustomer(customerManager.get(orderCreateRequest.getCustomerId()));
        order.setShippingAddress(customerManager.getAddress(orderCreateRequest.getShippingAddressId()));
        order.setBillingAddress(customerManager.getAddress(orderCreateRequest.getBillingAddressId()));
        order.setCreatedAt(new Date());

        Double totalPrice = 0d;
        List<OrderProduct> products = new ArrayList<OrderProduct>();
        for(CartItem cartItem : cartItems){
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProduct(cartItem.getProduct());
            orderProduct.setUnitPrice(cartItem.getProduct().getUnitPrice());
            orderProduct.setQuantity(cartItem.getQuantity());
            orderProduct.setStatus(OrderProductStatus.ORDERED);
            totalPrice = totalPrice + orderProduct.getUnitPrice() * orderProduct.getQuantity();
            products.add(orderProduct);
        }

        order.setTotalPrice(totalPrice);
        order.setProducts(products);

        List<OrderPayment> payments = new ArrayList<OrderPayment>();
        OrderPayment orderPayment = new OrderPayment();
        orderPayment.setAmount(order.getTotalPrice());
        orderPayment.setCard(customerManager.getCreditCard(orderCreateRequest.getCreditCardId()));
        orderPayment.setPaidAt(new Date());
        payments.add(orderPayment);

        order.setPayments(payments);
        orderManager.create(order);

        return new OrderRepresentation(order);
    }
}
