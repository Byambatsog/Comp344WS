package com.comp344.ecommerce.service.representation;

import com.comp344.ecommerce.domain.ProductCategory;

/**
 * Created by Byambatsog on 10/31/16.
 */
public class CategoryRepresentation extends BaseRepresentation {

    private Integer id;
    private String name;
    private String description;

    public CategoryRepresentation(){}

    public CategoryRepresentation(ProductCategory category){
        this.id = category.getId();
        this.name = category.getName();
        this.description = category.getDescription() != null ? category.getDescription() : "";
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
