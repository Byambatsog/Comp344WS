package com.comp344.ecommerce.service.representation;

import com.comp344.ecommerce.domain.CartItem;

/**
 * Created by Byambatsog on 10/31/16.
 */
public class CartItemRepresentation {

    private Integer id;
    private Integer productId;
    private String productName;
    private String productPicture;
    private Double unitPrice;
    private Integer quantity;

    public CartItemRepresentation(){}

    public CartItemRepresentation(CartItem item){
        this.id = item.getId();
        this.productId = item.getProduct().getId();
        this.productName = item.getProduct().getName();
        this.productPicture = item.getProduct().getPicture();
        this.unitPrice = item.getProduct().getUnitPrice();
        this.quantity = item.getQuantity();
    }

    public Integer getId() {
        return id;
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
}
