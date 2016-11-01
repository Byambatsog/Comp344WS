package com.comp344.ecommerce.service.representation;

import com.comp344.ecommerce.domain.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Byambatsog on 10/31/16.
 */
public class OrderRepresentation {

    private Double totalPrice;
    private String createdAt;
    private String paidAt;
    private String shippingAddress;
    private String billingAddress;
    private Integer customerId;
    private String customerFirstName;
    private List<OrderProductRepresentation> products = new ArrayList<OrderProductRepresentation>();
    private List<OrderPaymentRepresentation> payments = new ArrayList<OrderPaymentRepresentation>();
    private List<OrderStatusRepresentation> statuses = new ArrayList<OrderStatusRepresentation>();

    public OrderRepresentation(){}

    public OrderRepresentation(Order order){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        this.totalPrice = order.getTotalPrice();
        this.createdAt = dateFormat.format(order.getCreatedAt());
        this.paidAt = order.getPaidAt() != null ? dateFormat.format(order.getPaidAt()) : "";
        this.shippingAddress = order.getShippingAddress().getAddress();
        this.billingAddress = order.getBillingAddress().getAddress();
        this.customerId = order.getCustomer().getId();
        this.customerFirstName = order.getCustomer().getFirstName();

        if(order.getProducts() != null && order.getProducts().size() > 0)
            for(OrderProduct orderProduct : order.getProducts())
                this.products.add(new OrderProductRepresentation(orderProduct));

        if(order.getPayments() != null && order.getPayments().size() > 0)
            for(OrderPayment orderPayment : order.getPayments())
                this.payments.add(new OrderPaymentRepresentation(orderPayment));

        if(order.getStatuses() != null && order.getStatuses().size() > 0)
            for(OrderStatus orderStatus : order.getStatuses())
                this.statuses.add(new OrderStatusRepresentation(orderStatus));
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getPaidAt() {
        return paidAt;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public List<OrderProductRepresentation> getProducts() {
        return products;
    }

    public List<OrderPaymentRepresentation> getPayments() {
        return payments;
    }

    public List<OrderStatusRepresentation> getStatuses() {
        return statuses;
    }
}
