package com.comp344.ecommerce.service.representation;

import com.comp344.ecommerce.domain.Product;

import java.text.SimpleDateFormat;

/**
 * Created by Byambatsog on 10/31/16.
 */
public class PartnerProductRepresentation {

    private Integer id;
    private String name;
    private String picture;
    private String brandName;
    private String description;
    private Boolean status;
    private Integer quantityInStock;
    private Double unitPrice;
    private Double weight;
    private String createdAt;
    private Integer categoryId;
    private String categoryName;

    public PartnerProductRepresentation(){}

    public PartnerProductRepresentation(Product product){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        this.id = product.getId();
        this.name = product.getName() != null ? product.getName() : "";
        this.picture = product.getPicture() != null ? product.getPicture() : "";
        this.brandName = product.getBrandName() != null ? product.getBrandName() : "";
        this.description = product.getDescription() != null ? product.getDescription() : "";
        this.status = product.getStatus();
        this.quantityInStock = product.getQuantityInStock();
        this.unitPrice = product.getUnitPrice();
        this.weight = product.getWeight();
        this.createdAt = product.getCreatedAt() != null ? dateFormat.format(product.getCreatedAt()) : "";
        this.categoryId = product.getCategory().getId();
        this.categoryName = product.getCategory().getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }
    public String getBrandName() {
        return brandName;
    }
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Double getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }
    public Double getWeight() {
        return weight;
    }
    public void setWeight(Double weight) {
        this.weight = weight;
    }
    public Integer getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public Boolean getStatus() {
        return status;
    }
    public void setStatus(Boolean status) {
        this.status = status;
    }
    public Integer getQuantityInStock() {
        return quantityInStock;
    }
    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
