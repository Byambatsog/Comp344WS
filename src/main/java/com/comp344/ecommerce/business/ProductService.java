package com.comp344.ecommerce.business;

import com.comp344.ecommerce.dao.HibernateProductCategoryRepository;
import com.comp344.ecommerce.dao.HibernateProductRepository;
import com.comp344.ecommerce.domain.Product;
import com.comp344.ecommerce.domain.ProductCategory;
import com.comp344.ecommerce.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Byambatsog on 10/2/16.
 */
@Service("productService")
public class ProductService {

    @Autowired
    private HibernateProductRepository productRepository;

    @Autowired
    private HibernateProductCategoryRepository categoryRepository;

    public void save(Product product) throws Exception {
        productRepository.save(product);
    }

    public Product get(Integer id) throws Exception {
        return productRepository.get(id);
    }

    public void delete(Integer id) throws Exception {
        Product product = productRepository.get(id);
        productRepository.delete(product);
    }

    public Page<Product> find(String name, Integer categoryId, Integer partnerId, String brandName, Boolean status,
                              String orderBy, int page, int size){
        return productRepository.find(name, categoryId, partnerId, brandName, status, orderBy, page, size);
    }

    public Boolean checkAvailibilty(Integer productId, Integer quantity){
        Product product = productRepository.get(productId);
        if(product!=null && product.getQuantityInStock() > quantity)
            return Boolean.TRUE;
        else
            return Boolean.FALSE;
    }

    public void saveCategory(ProductCategory category) throws Exception {
        categoryRepository.save(category);
    }

    public ProductCategory getCategory(Integer categoryId) throws Exception {
        return categoryRepository.get(categoryId);
    }

    public void deleteCategory(Integer categoryId) throws Exception {
        ProductCategory category = categoryRepository.get(categoryId);
        categoryRepository.delete(category);
    }

    public Page<ProductCategory> findCategories(String name, Boolean status, String orderBy, int page, int size){
        return categoryRepository.find(name, status, orderBy, page, size);
    }
}
