package com.comp344.ecommerce.service.workflow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.comp344.ecommerce.service.representation.PartnerProductRepresentation;
import com.comp344.ecommerce.utils.ListPage;
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



//	public List<ProductRepresentation> searchProduct(String searchQuery, int page, int pageSize){
//		Page<Product> list = productManager.find(searchQuery, null, null, null, true, null, page, pageSize);
//		List<ProductRepresentation> products = new ArrayList<ProductRepresentation>();
//		for(Product product : list.getElements()){
//			products.add(new ProductRepresentation(product));
//		}
//		return products;
//	}
	
	
}
