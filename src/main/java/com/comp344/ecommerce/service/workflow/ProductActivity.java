package com.comp344.ecommerce.service.workflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.comp344.ecommerce.exception.ResourceNotFoundException;
import com.comp344.ecommerce.service.representation.ProductDetailRepresentation;
import com.comp344.ecommerce.utils.ListPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comp344.ecommerce.business.ProductManager;
import com.comp344.ecommerce.domain.Product;
import com.comp344.ecommerce.service.representation.ProductRepresentation;
import com.comp344.ecommerce.utils.Page;

@Component
public class ProductActivity {
	
	@Autowired
    private ProductManager productManager;
	
	public Page<ProductRepresentation> findProducts(String searchQuery, Integer categoryId, Integer partnerId,
													String orderBy, int page, int pageSize) throws Exception{

		if(orderBy != null && !orderBy.equals("")){
			if (orderBy.equals("created")){
				orderBy = "order by created_at desc";
			} else if (orderBy.equals("name")){
				orderBy = "order by name asc";
			}
		}

		Page<Product> products = productManager.find(searchQuery, categoryId, partnerId, Boolean.TRUE, orderBy, page, pageSize);
		List<ProductRepresentation> productRepresentations = new ArrayList<ProductRepresentation>();
		for(Product product : products.getElements()){
			productRepresentations.add(new ProductRepresentation(product));
		}
		Page<ProductRepresentation> productPage = new ListPage<ProductRepresentation>(productRepresentations, products.getPageNumber(), products.getPageSize(), products.getTotalNumberOfElements());
		return productPage;
	}

	public ProductDetailRepresentation getProduct(Integer id) throws Exception {
		Product product = productManager.get(id);
		if(product == null) {
			throw new ResourceNotFoundException("No product found with id " + id);
		}
		return new ProductDetailRepresentation(product);
	}


}
