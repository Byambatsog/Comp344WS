package com.comp344.ecommerce.service.representation;

import com.comp344.ecommerce.domain.OrderProduct;
import com.comp344.ecommerce.domain.OrderProductStatus;

/**
 * Created by Byambatsog on 10/31/16.
 */
public class OrderProductRepresentation {

    private Integer productId;
    private String productName;
    private String productPicture;
    private Double unitPrice;
    private Integer quantity;
    private OrderProductStatus status;

    public OrderProductRepresentation(){}

    public OrderProductRepresentation(OrderProduct orderProduct){
        this.productId = orderProduct.getProduct().getId();
        this.productName = orderProduct.getProduct().getName();
        this.productPicture = orderProduct.getProduct().getPicture();
        this.unitPrice = orderProduct.getUnitPrice();
        this.quantity = orderProduct.getQuantity();
        this.status = orderProduct.getStatus();
    }

    public Integer getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductPicture() {
        return productPicture;
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
