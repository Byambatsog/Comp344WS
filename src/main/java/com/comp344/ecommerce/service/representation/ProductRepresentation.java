package com.comp344.ecommerce.service.representation;

import com.comp344.ecommerce.domain.Product;

public class ProductRepresentation {
	
	private Integer id;
	private String name;
	private String picture;
	private String brandName;
	private String description;
	private Double unitPrice;
	private Double weight;
	private Integer categoryId;
	private String categoryName;
	private Integer partnerId;
	private String partnerName;
	
	public ProductRepresentation(){}
	
	public ProductRepresentation(Product product){
		
		this.id = product.getId();
		this.name = product.getName();
		this.picture = product.getPicture();
		this.brandName = product.getBrandName();
		this.description = product.getDescription();
		this.unitPrice = product.getUnitPrice();
		this.weight = product.getWeight();
		this.categoryId = product.getCategory().getId();
		this.categoryName = product.getCategory().getName();
		this.partnerId = product.getPartner().getId();
		if(product.getPartner().getCompanyName() != null && !product.getPartner().getCompanyName().equals(""))
			this.partnerName = product.getPartner().getCompanyName();
		else
			this.partnerName = product.getPartner().getFirstName() + " " + product.getPartner().getLastName();
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
	public Integer getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
}
