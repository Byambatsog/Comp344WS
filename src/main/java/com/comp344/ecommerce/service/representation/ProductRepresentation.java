package com.comp344.ecommerce.service.representation;

import com.comp344.ecommerce.domain.Product;

public class ProductRepresentation extends BaseRepresentation {

	private Integer id;
	private String name;
	private String picture;
	private Double unitPrice;
	private Integer partnerId;
	private String partnerName;
	
	public ProductRepresentation(){}
	
	public ProductRepresentation(Product product){

		this.id = product.getId();
		this.name = product.getName();
		this.picture = product.getPicture();
		this.unitPrice = product.getUnitPrice();
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
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
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
