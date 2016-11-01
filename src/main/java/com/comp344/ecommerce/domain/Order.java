package com.comp344.ecommerce.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Byambatsog on 10/2/16.
 */
@Entity
@Table(name="orders")
public class Order implements Serializable {

    private static final long serialVersionUID = 1461313260131251904L;

    private Integer id;
    private Double totalPrice;
    private Date paidAt;
    private Date createdAt;
    private Customer customer;
    private CustomerAddress shippingAddress;
    private CustomerAddress billingAddress;
    private CreditCard paymentCard;
    private OrderStatusType lastStatus;

    private List<OrderProduct> products;
    private List<OrderStatus> statuses;

    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "total_price")
    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Column(name = "paid_at")
    public Date getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(Date paidAt) {
        this.paidAt = paidAt;
    }

    @Column(name = "created_at")
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "last_status")
    public OrderStatusType getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(OrderStatusType lastStatus) {
        this.lastStatus = lastStatus;
    }

    @JoinColumn(name = "customers_id")
    @ManyToOne
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @JoinColumn(name = "shipping_addresses_id")
    @ManyToOne
    public CustomerAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(CustomerAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @JoinColumn(name = "billing_addresses_id")
    @ManyToOne
    public CustomerAddress getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(CustomerAddress billingAddress) {
        this.billingAddress = billingAddress;
    }

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<OrderProduct> getProducts() {
        return products;
    }

    public void setProducts(List<OrderProduct> products) {
        this.products = products;
    }

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    public List<OrderStatus> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<OrderStatus> statuses) {
        this.statuses = statuses;
    }

    @JoinColumn(name = "credit_cards_id")
    @ManyToOne
    public CreditCard getPaymentCard() {
        return paymentCard;
    }

    public void setPaymentCard(CreditCard paymentCard) {
        this.paymentCard = paymentCard;
    }
}
