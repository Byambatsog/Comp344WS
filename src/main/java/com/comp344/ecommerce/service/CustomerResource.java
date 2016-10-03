package com.comp344.ecommerce.service;

import com.comp344.ecommerce.business.CustomerService;
import com.comp344.ecommerce.domain.CreditCard;
import com.comp344.ecommerce.domain.Customer;
import com.comp344.ecommerce.domain.CustomerAddress;
import com.comp344.ecommerce.domain.Login;
import com.comp344.ecommerce.utils.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public void create() throws Exception {

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

        CustomerAddress address = new CustomerAddress();
        address.setStreet("1246 W Pratt blvd");
        address.setCity("Chicago");
        address.setState("IL");
        address.setZipCode("60626");
        address.setCountry("United States");
        address.setPhone("7734567212");
        address.setCreatedAt(new Date());
        address.setCustomer(customer);

        customerService.saveAddress(address);

        CreditCard card = new CreditCard();
        card.setCardNumber("4444555566667777");
        card.setCardType("Master");
        card.setExpireMonth(8);
        card.setExpireYear(2019);
        card.setNameOnCard("Byambatsog");
        card.setSecurityCode("221");
        card.setCreatedAt(new Date());
        card.setCustomer(customer);

    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    @ResponseBody
    public void update() throws Exception {


    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public Customer get(@RequestParam(value = "id") Integer id) throws Exception {
        return customerService.get(id);
    }
}
