package com.comp344.ecommerce.service.workflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comp344.ecommerce.business.PartnerManager;
import com.comp344.ecommerce.business.ProductManager;
import com.comp344.ecommerce.domain.Product;
import com.comp344.ecommerce.service.representation.ProductRepresentation;
import com.comp344.ecommerce.service.representation.ProductRequest;
import com.comp344.ecommerce.utils.Page;

@Component
public class ProductActivity {
	
	@Autowired
    private ProductManager productManager;
	
	@Autowired
	private PartnerManager partnerManager;

	public ProductRepresentation createProduct(ProductRequest productRequest) throws Exception {
		
		Product product = new Product();
    	product.setId(productRequest.getId());
    	product.setName(productRequest.getName());
    	product.setPicture(productRequest.getPicture());
    	product.setBrandName(productRequest.getBrandName());
    	product.setDescription(productRequest.getDescription());
    	product.setStatus(productRequest.getStatus());
    	product.setQuantityInStock(productRequest.getQuantityInStock());
    	product.setUnitPrice(productRequest.getUnitPrice());
    	product.setWeight(productRequest.getWeight());
    	product.setCreatedAt(new Date());
    	product.setCategory(productManager.getCategory(productRequest.getCategoryId()));
    	product.setPartner(partnerManager.get(productRequest.getPartnerId()));
		productManager.save(product);
		ProductRepresentation productRep = new ProductRepresentation(product);
		return productRep;
	}
	
	public List<ProductRepresentation> searchProduct(String searchQuery, int page, int pageSize){
		Page<Product> list = productManager.find(searchQuery, null, null, null, true, null, page, pageSize);
		List<ProductRepresentation> products = new ArrayList<ProductRepresentation>();
		for(Product product : list.getThisPageElements()){
			products.add(new ProductRepresentation(product));
		}
		return products;
	}
	
	
}
