package com.comp344.ecommerce.service.representation;

/**
 * Created by Byambatsog on 10/31/16.
 */
public class CartItemRequest {

    private Integer productId;
    private Integer quantity;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
