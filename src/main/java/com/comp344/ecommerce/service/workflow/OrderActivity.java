package com.comp344.ecommerce.service.workflow;

import com.comp344.ecommerce.business.*;
import com.comp344.ecommerce.domain.*;
import com.comp344.ecommerce.exception.NotAvailableException;
import com.comp344.ecommerce.exception.ResourceNotFoundException;
import com.comp344.ecommerce.service.representation.*;
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

    public OrderRepresentation createOrder(OrderCreateRequest orderCreateRequest) throws Exception {

        if(orderCreateRequest.getCartItems() == null || orderCreateRequest.getCartItems().size()==0)
            throw new ResourceNotFoundException("Your cart is empty! Please add your product into your cart.");

        Order order = new Order();
        order.setCustomer(customerManager.get(orderCreateRequest.getCustomerId()));
        order.setShippingAddress(customerManager.getAddress(orderCreateRequest.getShippingAddressId()));
        order.setBillingAddress(customerManager.getAddress(orderCreateRequest.getBillingAddressId()));
        order.setCreatedAt(new Date());

        Double totalPrice = 0d;
        List<OrderProduct> products = new ArrayList<OrderProduct>();
        for(CartItem cartItem : orderCreateRequest.getCartItems()){

            Product product = productManager.get(cartItem.getProductId());
            if (product == null) {
                throw new NotAvailableException("The product '" + product.getName() + "' is no longer available");
            } else if (product.getQuantityInStock() < cartItem.getQuantity()) {
                throw new NotAvailableException("Sorry, we don't have enough number of product '" + product.getName() + "' that you want to buy");
            }

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(product);
            orderProduct.setUnitPrice(product.getUnitPrice());
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

        return new OrderRepresentation(order, Boolean.TRUE, Boolean.TRUE);
    }

    public OrderRepresentation getOrder(Integer id) throws Exception {

        Order order = orderManager.get(id);
        if(order == null) {
            throw new ResourceNotFoundException("No order found with id " + id);
        }

        order.setProducts(orderManager.findProduct(id, null, null, null, null));
        order.setStatuses(orderManager.findStatuses(id, null, null));

        return new OrderRepresentation(order, Boolean.TRUE, Boolean.TRUE);
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

    public List<OrderRepresentation> getPartnerOrders(Integer partnerId, OrderProductStatus status) throws Exception {
        List<Order> orders = orderManager.findByPartner(partnerId, status, null, 0, 0).getElements();

        List<OrderRepresentation> orderRepresentations = new ArrayList<OrderRepresentation>();
        for(Order order : orders){
            Double totalPrice = 0d;
            List<OrderProduct> orderProducts = orderManager.findProduct(order.getId(), null, partnerId, status, null);
            for(OrderProduct orderProduct : orderProducts){
                totalPrice = totalPrice + orderProduct.getQuantity() * orderProduct.getUnitPrice();
            }
            order.setTotalPrice(totalPrice);
            order.setProducts(orderProducts);
            orderRepresentations.add(new OrderRepresentation(order, Boolean.TRUE, Boolean.FALSE));
        }
        return orderRepresentations;

    }

    public void fulfillOrderProduct(Integer partnerId, Integer orderId, Integer productId, String trackingNumber) throws Exception {

        OrderProduct orderProduct = null;
        List<OrderProduct> orderProducts = orderManager.findProduct(orderId, productId, partnerId, OrderProductStatus.ORDERED, null);
        if(orderProducts != null && orderProducts.size() > 0)
            orderProduct = orderProducts.get(0);

        if(orderProduct == null)
            throw new ResourceNotFoundException("No order product is found with productId " + productId);

        orderProduct.setStatus(OrderProductStatus.FULFILLED);
        orderProduct.setTrackingNumber(trackingNumber);
        orderManager.saveProduct(orderProduct);

        orderProducts = orderManager.findProduct(orderId, null, null, null, null);
        boolean allfulfilled = true;
        for(OrderProduct ordProduct : orderProducts){
            if(!ordProduct.getStatus().equals(OrderProductStatus.FULFILLED))
                allfulfilled = false;
        }

        if(allfulfilled){
            Order order = orderManager.get(orderId);
            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setCreatedAt(new Date());
            orderStatus.setStatus(OrderStatusType.FULFILLED);
            orderStatus.setOrder(order);
            orderManager.saveStatus(orderStatus);
            order.setLastStatus(OrderStatusType.FULFILLED);
            orderManager.save(order);
        }

    }

    public void shipOrderProduct(Integer partnerId, Integer orderId, Integer productId) throws Exception {

        OrderProduct orderProduct = null;
        List<OrderProduct> orderProducts = orderManager.findProduct(orderId, productId, partnerId, OrderProductStatus.FULFILLED, null);
        if(orderProducts != null && orderProducts.size() > 0)
            orderProduct = orderProducts.get(0);

        if(orderProduct == null)
            throw new ResourceNotFoundException("No order product is found with productId " + productId);


        orderProduct.setStatus(OrderProductStatus.SHIPPED);
        orderManager.saveProduct(orderProduct);

        orderProducts = orderManager.findProduct(orderId, null, null, null, null);
        boolean allshipped = true;
        for(OrderProduct ordProduct : orderProducts){
            if(!ordProduct.getStatus().equals(OrderProductStatus.SHIPPED))
                allshipped = false;
        }

        if(allshipped){
            Order order = orderManager.get(orderId);
            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setCreatedAt(new Date());
            orderStatus.setStatus(OrderStatusType.SHIPPED);
            orderStatus.setOrder(order);
            orderManager.saveStatus(orderStatus);
            order.setLastStatus(OrderStatusType.SHIPPED);
            orderManager.save(order);
        }
    }

    public void setOrderProductStatusDelivered(Integer partnerId, Integer orderId, Integer productId) throws Exception {
        OrderProduct orderProduct = null;
        List<OrderProduct> orderProducts = orderManager.findProduct(orderId, productId, partnerId, OrderProductStatus.SHIPPED, null);
        if(orderProducts != null && orderProducts.size() > 0)
            orderProduct = orderProducts.get(0);

        if(orderProduct == null)
            throw new ResourceNotFoundException("No order product is found with productId " + productId);

        orderProduct.setStatus(OrderProductStatus.DELIVERED);
        orderManager.saveProduct(orderProduct);

        orderProducts = orderManager.findProduct(orderId, null, null, null, null);
        boolean alldelivered = true;
        for(OrderProduct ordProduct : orderProducts){
            if(!ordProduct.getStatus().equals(OrderProductStatus.DELIVERED))
                alldelivered = false;
        }

        System.out.println("alldelivered: " + alldelivered);

        if(alldelivered){
            Order order = orderManager.get(orderId);
            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setCreatedAt(new Date());
            orderStatus.setStatus(OrderStatusType.DELIVERED);
            orderStatus.setOrder(order);
            orderManager.saveStatus(orderStatus);
            order.setLastStatus(OrderStatusType.DELIVERED);
            orderManager.save(order);
        }
    }

    public void cancelOrder(Integer id) throws Exception {
        Order order = orderManager.get(id);

        if(order == null ) {
            throw new ResourceNotFoundException("No order found with id " + id);
        }

        //authenticate()

        if(order.getLastStatus().equals(OrderStatusType.SHIPPED)
            || order.getLastStatus().equals(OrderStatusType.DELIVERED)
            || order.getLastStatus().equals(OrderStatusType.CANCELLED)){
            throw new NotAvailableException("Sorry, your order has been already '" + order.getLastStatus().name() + "'");
        }

        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setCreatedAt(new Date());
        orderStatus.setStatus(OrderStatusType.CANCELLED);
        orderStatus.setOrder(order);
        orderManager.saveStatus(orderStatus);
        order.setLastStatus(OrderStatusType.CANCELLED);
        orderManager.save(order);

        //todo
        //refund function would be here

    }
}
