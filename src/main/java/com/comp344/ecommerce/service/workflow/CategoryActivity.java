package com.comp344.ecommerce.service.workflow;

import com.comp344.ecommerce.business.ProductManager;
import com.comp344.ecommerce.domain.ProductCategory;
import com.comp344.ecommerce.exception.ResourceNotFoundException;
import com.comp344.ecommerce.service.representation.CategoryRepresentation;
import com.comp344.ecommerce.utils.ListPage;
import com.comp344.ecommerce.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Byambatsog on 10/31/16.
 */
@Component
public class CategoryActivity {

    @Autowired
    private ProductManager productManager;

    public CategoryRepresentation getCategory(Integer id) throws Exception {
        ProductCategory category = productManager.getCategory(id);
        if(category == null) {
            throw new ResourceNotFoundException("No category found with id " + id);
        }
        return new CategoryRepresentation(category);
    }

    public Page<CategoryRepresentation> getAllCategories() throws Exception{

        Page<ProductCategory> list = productManager.findCategories(null, Boolean.TRUE, null, 0, 0);
        List<CategoryRepresentation> categoryRepresentations = new ArrayList<CategoryRepresentation>();
        for(ProductCategory category : list.getElements()){
            categoryRepresentations.add(new CategoryRepresentation(category));
        }
        Page<CategoryRepresentation> partnerPage = new ListPage<CategoryRepresentation>(categoryRepresentations, list.getPageNumber(), list.getPageSize(), list.getTotalNumberOfElements());
        return partnerPage;
    }
}
