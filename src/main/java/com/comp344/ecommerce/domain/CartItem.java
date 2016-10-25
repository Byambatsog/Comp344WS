package com.comp344.ecommerce.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="customer_cart_items")
public class CartItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -553010205763109276L;
	
	private Integer id;
	private Cart customerCart;
	private Product product;
	private Integer quantity;
	
	@Id
    @GeneratedValue
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@JoinColumn(name = "customer_carts_id")
    @ManyToOne
	public Cart getCustomerCart() {
		return customerCart;
	}

	public void setCustomerCart(Cart customerCart) {
		this.customerCart = customerCart;
	}

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
}
