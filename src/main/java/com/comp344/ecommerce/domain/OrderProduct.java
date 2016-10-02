package com.comp344.ecommerce.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Byambatsog on 10/2/16.
 */
@Entity
@Table(name="order_products")
public class OrderProduct implements Serializable {

    private static final long serialVersionUID = 8416353497990613291L;

    private Order order;
    private Product product;
    private Integer quantity;
    private Double unitPrice;
    private OrderProductStatus status;

    @Id
    @JoinColumn(name = "orders_id")
    @ManyToOne
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Id
    @JoinColumn(name = "products_id")
    @ManyToOne
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Column(name = "unit_price")
    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Enumerated(EnumType.STRING)
    public OrderProductStatus getStatus() {
        return status;
    }

    public void setStatus(OrderProductStatus status) {
        this.status = status;
    }
}
