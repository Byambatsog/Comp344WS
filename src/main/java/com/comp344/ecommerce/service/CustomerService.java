package com.comp344.ecommerce.service;

import com.comp344.ecommerce.dao.HibernateCustomerRepository;
import com.comp344.ecommerce.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;


/**
 * Created by Byambatsog on 9/27/16.
 */
@Controller("customerService")
@RequestMapping("/")
public class CustomerService {

    @Autowired
    private HibernateCustomerRepository repository;

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    @ResponseBody
    public Customer init() throws Exception {

        Customer customer = new Customer();
        customer.setFirstName("Byambatsog");
        customer.setLastName("Chimed");
        customer.setEmail("Email");
        customer.setCreatedAt(new Date());
        repository.save(customer);
        return customer;
    }

}
