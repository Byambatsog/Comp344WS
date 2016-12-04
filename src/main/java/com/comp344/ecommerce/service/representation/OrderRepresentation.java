package com.comp344.ecommerce.service.representation;

import com.comp344.ecommerce.domain.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Byambatsog on 10/31/16.
 */
public class OrderRepresentation extends BaseRepresentation {

    private Integer id;
    private Double totalPrice;
    private String createdAt;
    private String paidAt;
    private String shippingAddress;
    private String billingAddress;
    private Integer customerId;
    private String customerFirstName;
    private String paymentCardNumber;
    private String paymentCardType;
    private String lastStatus;
    private List<OrderProductRepresentation> products = new ArrayList<OrderProductRepresentation>();
    private List<OrderStatusRepresentation> statuses = new ArrayList<OrderStatusRepresentation>();

    public OrderRepresentation(){}

    public OrderRepresentation(Order order){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        this.id = order.getId();
        this.totalPrice = order.getTotalPrice();
        this.createdAt = dateFormat.format(order.getCreatedAt());
        this.paidAt = order.getPaidAt() != null ? dateFormat.format(order.getPaidAt()) : "";
        this.lastStatus = order.getLastStatus().name();
        this.shippingAddress = order.getShippingAddress().getAddress();
        this.billingAddress = order.getBillingAddress().getAddress();
        this.customerId = order.getCustomer().getId();
        this.customerFirstName = order.getCustomer().getFirstName();
        this.paymentCardNumber = "..." + order.getPaymentCard().getCardNumber().substring(order.getPaymentCard().getCardNumber().length() - 4);
        this.paymentCardType = order.getPaymentCard().getCardType();
    }

    public Integer getId() {
        return id;
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

    public String getPaymentCardNumber() {
        return paymentCardNumber;
    }

    public String getPaymentCardType() {
        return paymentCardType;
    }

    public String getLastStatus() {
        return lastStatus;
    }

    public List<OrderProductRepresentation> getProducts() {
        return products;
    }

    public List<OrderStatusRepresentation> getStatuses() {
        return statuses;
    }

    public void setProducts(List<OrderProductRepresentation> products) {
        this.products = products;
    }

    public void setStatuses(List<OrderStatusRepresentation> statuses) {
        this.statuses = statuses;
    }
}
