package com.comp344.ecommerce.business;

import com.comp344.ecommerce.dao.HibernateCustomerRepository;
import com.comp344.ecommerce.dao.HibernateLoginRepository;
import com.comp344.ecommerce.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * Created by Byambatsog on 9/27/16.
 */
@Service("customerService")
public class CustomerService {

    @Autowired
    private HibernateCustomerRepository customerRepository;

    @Autowired
    private HibernateLoginRepository loginRepository;

    public Customer create(Customer customer) throws Exception {
        loginRepository.save(customer.getLogin());
        customerRepository.save(customer);
        return customer;
    }

    public Customer update(Customer customer) throws Exception {
        customerRepository.save(customer);
        return customer;
    }

    public Customer get(Integer id) throws Exception {
        return customerRepository.get(id);
    }



}
