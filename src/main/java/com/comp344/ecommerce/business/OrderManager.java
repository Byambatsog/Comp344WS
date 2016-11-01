package com.comp344.ecommerce.business;

import com.comp344.ecommerce.dao.*;
import com.comp344.ecommerce.domain.*;
import com.comp344.ecommerce.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Byambatsog on 10/2/16.
 */
@Service("orderService")
public class OrderManager {

    @Autowired
    private HibernateOrderRepository orderRepository;

    @Autowired
    private HibernateOrderProductRepository orderProductRepository;

    @Autowired
    private HibernateOrderStatusRepository orderStatusRepository;

    @Autowired
    private HibernateProductRepository productRepository;

    @Transactional
    public void create(Order order) throws Exception {

        orderRepository.save(order);

        for(OrderProduct orderProduct : order.getProducts()){
            //orderProduct.setOrder(order);
            //orderProductRepository.save(orderProduct);
            Product product = productRepository.get(orderProduct.getProduct().getId());
            product.setQuantityInStock(product.getQuantityInStock() - orderProduct.getQuantity());
            productRepository.save(product);
        }

        OrderStatus status = new OrderStatus();
        status.setOrder(order);
        status.setStatus(OrderStatusType.ORDERED);
        status.setCreatedAt(new Date());
        List<OrderStatus> statuses = new ArrayList<OrderStatus>();
        statuses.add(status);
        order.setStatuses(statuses);

        //orderStatusRepository.save(status);

        orderRepository.save(order);
    }

    public Order save(Order order) throws Exception {
        orderRepository.save(order);
        return order;
    }

    public Order get(Integer id) throws Exception {
        return orderRepository.get(id);
    }

    @Transactional
    public void delete(Integer id) throws Exception {
        Order order = orderRepository.get(id);
        List<OrderProduct> products = orderProductRepository.find(order.getId(), null, null, null, null);
        for(OrderProduct product : products){
            orderProductRepository.delete(product);
        }

        List<OrderStatus> statuses = orderStatusRepository.find(order.getId(), null, null);
        for(OrderStatus status : statuses){
            orderStatusRepository.delete(status);
        }

        orderRepository.delete(order);
    }

    public Page<Order> find(Integer customerId, Integer shippingAddresssId, Integer billingAddressId,
                            String orderBy, int page, int size){
        return orderRepository.find(customerId, shippingAddresssId, billingAddressId, orderBy, page, size);
    }

    public void saveProduct(OrderProduct product){
        orderProductRepository.save(product);
    }

    public void deleteProduct(Integer productId){
        OrderProduct product = orderProductRepository.get(productId);
        orderProductRepository.delete(product);
    }

    public OrderProduct getProduct(Integer productId){
        return orderProductRepository.get(productId);
    }

    public List<OrderProduct> findProduct(Integer orderId, Integer productId, Integer partnerId, OrderProductStatus status, String orderBy){
        return orderProductRepository.find(orderId, productId, partnerId, status, orderBy);
    }

    public void saveStatus(OrderStatus status){
        orderStatusRepository.save(status);
    }

    public void deleteStatus(Integer statusId){
        OrderStatus status = orderStatusRepository.get(statusId);
        orderStatusRepository.delete(status);
    }

    public OrderStatus getStatus(Integer statusId){
        return orderStatusRepository.get(statusId);
    }

    public List<OrderStatus> findStatuses(Integer orderId, OrderStatus status, String orderBy){
        return orderStatusRepository.find(orderId, status, orderBy);
    }

    public OrderStatusType getLastStatusType(Integer orderId){
        OrderStatus status = orderStatusRepository.find(orderId, null, null).get(0);
        if(status != null)
            return status.getStatus();
        else
            return null;
    }

    public OrderProductStatus getLastOrderProductStatus(Integer orderId, Integer productId){
        OrderProduct orderProduct = orderProductRepository.find(orderId, productId, null, null, null).get(0);
        if(orderProduct != null)
            return orderProduct.getStatus();
        else
            return null;
    }

}
