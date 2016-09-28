package com.comp344.ecommerce.dao;

import com.comp344.ecommerce.domain.Customer;
import com.comp344.ecommerce.utils.Page;

/**
 * Created by Byambatsog on 9/27/16.
 */
public interface CustomerRepository extends BaseRepo<Customer>  {

    public void save(Customer customer);

    public Customer get(Integer id);

    public void delete(Customer customer);

    public Page<Customer> list(String firstName, String lastName, String email, String orderBy, int page, int size);

}
