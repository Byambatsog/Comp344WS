package com.comp344.ecommerce.service.workflow;

import com.comp344.ecommerce.business.*;
import com.comp344.ecommerce.domain.*;
import com.comp344.ecommerce.exception.NotAvailableException;
import com.comp344.ecommerce.exception.ResourceNotFoundException;
import com.comp344.ecommerce.service.representation.OrderCreateRequest;
import com.comp344.ecommerce.service.representation.OrderProductRepresentation;
import com.comp344.ecommerce.service.representation.OrderRepresentation;
import com.comp344.ecommerce.service.representation.OrderStatusRepresentation;
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

            Product product = productManager.get(cartItem.getProduct().getId());
            if (product == null) {
                throw new NotAvailableException("The product '" + product.getName() + "' is no longer available");
            } else if (product.getQuantityInStock() < cartItem.getQuantity()) {
                throw new NotAvailableException("Sorry, we don't have enough number of product '" + product.getName() + "' that you want to buy");
            }

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(cartItem.getProduct());
            orderProduct.setUnitPrice(cartItem.getProduct().getUnitPrice());
            orderProduct.setQuantity(cartItem.getQuantity());
            orderProduct.setStatus(OrderProductStatus.ORDERED);
            totalPrice = totalPrice + orderProduct.getUnitPrice() * orderProduct.getQuantity();
            products.add(orderProduct);
        }

        order.setTotalPrice(totalPrice);
        order.setProducts(products);
        order.setPaymentCard(customerManager.getCreditCard(orderCreateRequest.getCreditCardId()));
        order.setLastStatus(OrderStatusType.ORDERED);
        order.setPaidAt(new Date());
        orderManager.create(order);

        cart.setStatus(Boolean.FALSE);
        cartManager.save(cart);

        return new OrderRepresentation(order);
    }

    public OrderRepresentation getOrder(Integer id) throws Exception {

        Order order = orderManager.get(id);
        if(order == null) {
            throw new ResourceNotFoundException("No order found with id " + id);
        }

        order.setProducts(orderManager.findProduct(id, null, null, null, null));
        order.setStatuses(orderManager.findStatuses(id, null, null));

        return new OrderRepresentation(order);
    }

    public List<OrderStatusRepresentation> getOrderStatuses(Integer orderId) throws Exception {

        Order order = orderManager.get(orderId);
        if(order == null) {
            throw new ResourceNotFoundException("No order found with id " + orderId);
        }

        List<OrderStatus> statuses = orderManager.findStatuses(orderId, null, null);

        List<OrderStatusRepresentation> statusRepresentations = new ArrayList<OrderStatusRepresentation>();
        for(OrderStatus status : statuses){
            statusRepresentations.add(new OrderStatusRepresentation(status));
        }
        return statusRepresentations;
    }

    public List<OrderProductRepresentation> getOrderProducts(Integer orderId) throws Exception {

        Order order = orderManager.get(orderId);
        if(order == null) {
            throw new ResourceNotFoundException("No order found with id " + orderId);
        }

        List<OrderProduct> products = orderManager.findProduct(orderId, null, null, null, null);

        List<OrderProductRepresentation> productRepresentations = new ArrayList<OrderProductRepresentation>();
        for(OrderProduct product : products){
            productRepresentations.add(new OrderProductRepresentation(product));
        }
        return productRepresentations;
    }

    public List<OrderRepresentation> getCustomerOrders(Integer customerId) throws Exception {
        List<Order> orders = orderManager.find(customerId, null, null, null, 0, 0).getElements();

        List<OrderRepresentation> orderRepresentations = new ArrayList<OrderRepresentation>();
        for(Order order : orders){
            orderRepresentations.add(new OrderRepresentation(order));
        }
        return orderRepresentations;

    }
}
