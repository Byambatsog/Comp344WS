package com.comp344.ecommerce.service.representation;

import com.comp344.ecommerce.domain.OrderProduct;
import com.comp344.ecommerce.domain.OrderProductStatus;

/**
 * Created by Byambatsog on 10/31/16.
 */
public class OrderProductRepresentation extends BaseRepresentation {

    private Integer id;
    private String name;
    private String picture;
    private Double unitPrice;
    private Integer quantity;
    private OrderProductStatus status;

    public OrderProductRepresentation(){}

    public OrderProductRepresentation(OrderProduct orderProduct){
        this.id = orderProduct.getProduct().getId();
        this.name = orderProduct.getProduct().getName();
        this.picture = orderProduct.getProduct().getPicture();
        this.unitPrice = orderProduct.getUnitPrice();
        this.quantity = orderProduct.getQuantity();
        this.status = orderProduct.getStatus();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public OrderProductStatus getStatus() {
        return status;
    }
}
