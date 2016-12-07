package com.comp344.ecommerce.service.workflow;

import com.comp344.ecommerce.business.*;
import com.comp344.ecommerce.domain.*;
import com.comp344.ecommerce.exception.NotAvailableException;
import com.comp344.ecommerce.exception.ResourceNotFoundException;
import com.comp344.ecommerce.jwt.AuthorizationUtil;
import com.comp344.ecommerce.service.representation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
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
    private AuthorizationUtil authorizationUtil;

    public OrderRepresentation createOrder(OrderCreateRequest orderCreateRequest) throws Exception {

        if(orderCreateRequest.getCartItems() == null || orderCreateRequest.getCartItems().size()==0)
            throw new ResourceNotFoundException("Your cart is empty! Please add your product into your cart.");

        Customer customer = authorizationUtil.getAuthenticatedCustomer();
        Order order = new Order();
        order.setCustomer(customer);
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

        OrderRepresentation orderRepresentation = new OrderRepresentation(order);
        for(OrderProduct orderProduct : order.getProducts()){
            orderRepresentation.getProducts().add(new OrderProductRepresentation(orderProduct));
        }
        for(OrderStatus orderStatus : order.getStatuses()){
            orderRepresentation.getStatuses().add(new OrderStatusRepresentation(orderStatus));
        }

        setLinks(orderRepresentation, order);

        return orderRepresentation;
    }

    public OrderRepresentation getOrder(Integer id) throws Exception {

        Order order = orderManager.get(id);
        if(order == null) {
            throw new ResourceNotFoundException("No order found with id " + id);
        }

        authorizationUtil.authorize(order.getCustomer());

        order.setProducts(orderManager.findProduct(id, null, null, null, null));
        order.setStatuses(orderManager.findStatuses(id, null, null));

        OrderRepresentation orderRepresentation = new OrderRepresentation(order);
        for(OrderProduct orderProduct : order.getProducts()){
            OrderProductRepresentation orderProductRepresentation = new OrderProductRepresentation(orderProduct);
            setLink(orderProductRepresentation, orderProduct);
            orderRepresentation.getProducts().add(orderProductRepresentation);
        }
        for(OrderStatus orderStatus : order.getStatuses()){
            orderRepresentation.getStatuses().add(new OrderStatusRepresentation(orderStatus));
        }
        setLinks(orderRepresentation, order);
        return orderRepresentation;
    }

    public List<OrderStatusRepresentation> getOrderStatuses(Integer orderId) throws Exception {

        Order order = orderManager.get(orderId);
        if(order == null) {
            throw new ResourceNotFoundException("No order found with id " + orderId);
        }
        authorizationUtil.authorize(order.getCustomer());

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

        authorizationUtil.authorize(order.getCustomer());

        List<OrderProduct> products = orderManager.findProduct(orderId, null, null, null, null);

        List<OrderProductRepresentation> productRepresentations = new ArrayList<OrderProductRepresentation>();
        for(OrderProduct product : products){
            productRepresentations.add(new OrderProductRepresentation(product));
        }
        return productRepresentations;
    }

    public void cancelOrder(Integer id) throws Exception {
        Order order = orderManager.get(id);

        if(order == null ) {
            throw new ResourceNotFoundException("No order found with id " + id);
        }

        authorizationUtil.authorize(order.getCustomer());

        boolean possibleToCancel = true;

        if(order.getLastStatus().equals(OrderStatusType.SHIPPED)
            || order.getLastStatus().equals(OrderStatusType.DELIVERED)
            || order.getLastStatus().equals(OrderStatusType.CANCELLED)){
            throw new NotAvailableException("Sorry, your order has been already '" + order.getLastStatus().name() + "'");
        } else {
            List<OrderProduct> orderProducts = orderManager.findProduct(id, null, null, null, null);
            for(OrderProduct orderProduct : orderProducts){
                if(orderProduct.getStatus().equals(OrderProductStatus.SHIPPED)
                        || orderProduct.getStatus().equals(OrderProductStatus.DELIVERED)){
                    possibleToCancel = false;
                    break;
                }
            }
        }

        if(!possibleToCancel){
            throw new NotAvailableException("Sorry, your order has been partially shipped or delivered.");
        }

        List<OrderProduct> orderProducts = orderManager.findProduct(id, null, null, null, null);
        for(OrderProduct orderProduct : orderProducts){
            orderProduct.setStatus(OrderProductStatus.CANCELLED);
            orderManager.saveProduct(orderProduct);
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

    public void cancelOrderProduct(Integer orderId, Integer productId) throws Exception {

        Order order = orderManager.get(orderId);
        authorizationUtil.authorize(order.getCustomer());

        OrderProduct orderProduct = null;
        List<OrderProduct> orderProducts = orderManager.findProduct(orderId, productId, null, null, null);
        if(orderProducts != null && orderProducts.size() > 0)
            orderProduct = orderProducts.get(0);

        if(orderProduct == null){
            throw new ResourceNotFoundException("No order product is found with productId " + productId);
        }

        if(orderProduct.getStatus().equals(OrderProductStatus.ORDERED)
                || orderProduct.getStatus().equals(OrderProductStatus.FULFILLED)) {
            orderProduct.setStatus(OrderProductStatus.CANCELLED);
            orderManager.saveProduct(orderProduct);
        }

        List<OrderProduct> products = orderManager.findProduct(orderId, null, null, null, null);
        double totalPrice = 0d;
        for(OrderProduct orProduct : products){
            if(!orProduct.getStatus().equals(OrderProductStatus.CANCELLED)){
                totalPrice = totalPrice + orProduct.getUnitPrice() * orProduct.getQuantity();
            }
        }
        order.setTotalPrice(totalPrice);
        orderManager.save(order);
    }

    private void setLinks(OrderRepresentation orderRepresentation, Order order) {
        orderRepresentation.addLink(BaseRepresentation.BASE_URI + "/orderservice/orders/" + order.getId(),
                "self", HttpMethod.GET, "");
        orderRepresentation.addLink(BaseRepresentation.BASE_URI + "/orderservice/orders/" + order.getId(),
                "cancel", HttpMethod.DELETE, "");
    }

    private void setLink(OrderProductRepresentation orderProductRepresentation, OrderProduct orderProduct) {
        orderProductRepresentation.addLink(BaseRepresentation.BASE_URI + "/orderservice/orders/" + orderProduct.getOrder().getId()
                + "/products/" + orderProduct.getProduct().getId(),
                "cancel", HttpMethod.DELETE, "");
    }
}
