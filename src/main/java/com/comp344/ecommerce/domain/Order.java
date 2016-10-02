package com.comp344.ecommerce.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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
    private Partner partner;

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

    @JoinColumn(name = "partners_id")
    @ManyToOne
    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }
}
