package com.comp344.ecommerce.service.workflow;

import com.comp344.ecommerce.business.ProductManager;
import com.comp344.ecommerce.domain.ProductCategory;
import com.comp344.ecommerce.exception.ResourceNotFoundException;
import com.comp344.ecommerce.service.representation.BaseRepresentation;
import com.comp344.ecommerce.service.representation.CategoryRepresentation;
import com.comp344.ecommerce.utils.ListPage;
import com.comp344.ecommerce.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
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
        CategoryRepresentation categoryRepresentation = new CategoryRepresentation(category);
        setLinks(categoryRepresentation, category);
        return categoryRepresentation;
    }

    public Page<CategoryRepresentation> getAllCategories() throws Exception{

        Page<ProductCategory> list = productManager.findCategories(null, Boolean.TRUE, null, 0, 0);
        List<CategoryRepresentation> categoryRepresentations = new ArrayList<CategoryRepresentation>();
        for(ProductCategory category : list.getElements()){
            CategoryRepresentation categoryRepresentation = new CategoryRepresentation(category);
            setLinks(categoryRepresentation, category);
            categoryRepresentations.add(categoryRepresentation);
        }
        Page<CategoryRepresentation> partnerPage = new ListPage<CategoryRepresentation>(categoryRepresentations, list.getPageNumber(), list.getPageSize(), list.getTotalNumberOfElements());
        return partnerPage;
    }

    private void setLinks(CategoryRepresentation categoryRepresentation, ProductCategory category) {
        categoryRepresentation.addLink(BaseRepresentation.BASE_URI + "/categoryservice/categories/" + category.getId(),
                "self", HttpMethod.GET, "");
        categoryRepresentation.addLink(BaseRepresentation.BASE_URI + "/productservice/products?categoryId=" + category.getId(),
                "products", HttpMethod.GET, "");

    }
}
