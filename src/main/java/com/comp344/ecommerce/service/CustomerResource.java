package com.comp344.ecommerce.service;

import com.comp344.ecommerce.business.CustomerService;
import com.comp344.ecommerce.domain.Customer;
import com.comp344.ecommerce.domain.Login;
import com.comp344.ecommerce.utils.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by Byambatsog on 10/2/16.
 */
@Controller
@RequestMapping("/customer")
public class CustomerResource {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    public Customer create() throws Exception {

        Customer customer = new Customer();
        customer.setFirstName("Byambatsog");
        customer.setLastName("Chimed");
        customer.setEmail("Email");
        customer.setCreatedAt(new Date());

        Login login = new Login();
        login.setEmail("bchimed@luc.edu");
        login.setUsername("byambatsog");
        PasswordEncoder passwordEncoder = new PasswordEncoder();
        login.setPassword(passwordEncoder.encode("123456789"));
        login.setActive(true);
        login.setAdmin(true);
        login.setCreatedAt(new Date());
        customer.setLogin(login);
        customerService.create(customer);
        return customer;
    }
}
