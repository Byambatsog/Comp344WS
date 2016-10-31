package com.comp344.ecommerce.business;

import com.comp344.ecommerce.dao.*;
import com.comp344.ecommerce.domain.*;
import com.comp344.ecommerce.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private HibernateOrderPaymentRepository paymentRepository;

    @Autowired
    private HibernateOrderStatusRepository orderStatusRepository;

    @Autowired
    private HibernateProductRepository productRepository;

    @Transactional
    public void create(Order order) throws Exception {
        orderRepository.save(order);


        for(OrderProduct orderProduct : order.getProducts()){

            orderProduct.setOrder(order);
            orderProductRepository.save(orderProduct);

            Product product = productRepository.get(orderProduct.getProduct().getId());
            product.setQuantityInStock(product.getQuantityInStock() - orderProduct.getQuantity());
            productRepository.save(product);

        }

        for(OrderPayment payment : order.getPayments()){
            payment.setOrder(order);
            paymentRepository.save(payment);
        }

        OrderStatus status = new OrderStatus();
        status.setOrder(order);
        status.setStatus(OrderStatusType.ORDERED);
        status.setCreatedAt(new Date());
        orderStatusRepository.save(status);

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
        List<OrderProduct> products = orderProductRepository.find(order.getId(), null, null, null, 0, 0).getElements();
        for(OrderProduct product : products){
            orderProductRepository.delete(product);
        }

        List<OrderPayment> payments = paymentRepository.find(order.getId(), null, null, 0, 0).getElements();
        for(OrderPayment payment : payments){
            paymentRepository.delete(payment);
        }

        List<OrderStatus> statuses = orderStatusRepository.find(order.getId(), null, null, 0, 0).getElements();
        for(OrderStatus status : statuses){
            orderStatusRepository.delete(status);
        }

        orderRepository.delete(order);
    }

    public Page<Order> find(Integer customerId, Integer shippingAddresssId, Integer billingAddressId, Integer partnerId,
                            String orderBy, int page, int size){
        return orderRepository.find(customerId, shippingAddresssId, billingAddressId, partnerId, orderBy, page, size);
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

    public Page<OrderProduct> find(Integer orderId, Integer productId, OrderProductStatus status, String orderBy,
                                   int page, int size){
        return orderProductRepository.find(orderId, productId, status, orderBy, page, size);
    }

    public void savePayment(OrderPayment payment){
        paymentRepository.save(payment);
    }

    public void deletePayment(Integer paymentId){
        OrderPayment payment = paymentRepository.get(paymentId);
        paymentRepository.delete(payment);
    }

    public OrderPayment getPayment(Integer paymentId){
        return paymentRepository.get(paymentId);
    }

    public Page<OrderPayment> find(Integer orderId, Integer creditCardId, String orderBy, int page, int size){
        return paymentRepository.find(orderId, creditCardId, orderBy, page, size);
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

    public Page<OrderStatus> find(Integer orderId, OrderStatus status, String orderBy, int page, int size){
        return orderStatusRepository.find(orderId, status, orderBy, page, size);
    }

}
