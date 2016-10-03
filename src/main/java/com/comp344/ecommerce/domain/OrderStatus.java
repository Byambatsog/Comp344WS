package com.comp344.ecommerce.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Byambatsog on 10/2/16.
 */
@Entity
@Table(name="order_status")
public class OrderStatus implements Serializable {

    private static final long serialVersionUID = 8643067510027651649L;

    private Integer id;
    private OrderStatusType status;
    private Order order;
    private Date createdAt;

    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Enumerated(EnumType.STRING)
    public OrderStatusType getStatus() {
        return status;
    }

    public void setStatus(OrderStatusType status) {
        this.status = status;
    }

    @JoinColumn(name = "orders_id")
    @ManyToOne
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Column(name = "created_at")
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
