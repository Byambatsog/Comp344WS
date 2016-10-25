package com.comp344.ecommerce.business;

import com.comp344.ecommerce.dao.HibernateCreditCardRepository;
import com.comp344.ecommerce.dao.HibernateCustomerAddressRepository;
import com.comp344.ecommerce.dao.HibernateCustomerRepository;
import com.comp344.ecommerce.dao.HibernateLoginRepository;
import com.comp344.ecommerce.domain.CreditCard;
import com.comp344.ecommerce.domain.Customer;
import com.comp344.ecommerce.domain.CustomerAddress;
import com.comp344.ecommerce.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by Byambatsog on 9/27/16.
 */
@Service("customerService")
public class CustomerManager {

    @Autowired
    private HibernateLoginRepository loginRepository;

    @Autowired
    private HibernateCustomerRepository customerRepository;

    @Autowired
    private HibernateCustomerAddressRepository addressRepository;

    @Autowired
    private HibernateCreditCardRepository creditCardRepository;

    public void create(Customer customer) throws Exception {
        loginRepository.save(customer.getLogin());
        customerRepository.save(customer);
    }

    public void update(Customer customer) throws Exception {
        customerRepository.save(customer);
    }

    public Customer get(Integer id) throws Exception {
        return customerRepository.get(id);

    }

    public Customer findByLogin(Integer loginId){
        return customerRepository.findByLogin(loginId);
    }

    public void delete(Integer id) throws Exception {
        Customer customer = customerRepository.get(id);
        loginRepository.delete(customer.getLogin());
        customerRepository.delete(customer);
    }

    public Page<Customer> find(String firstName, String lastName, String email, String orderBy, int page, int size){
        return customerRepository.find(firstName, lastName, email, orderBy, page, size);
    }

    public CustomerAddress saveAddress(CustomerAddress customerAddress){
        addressRepository.save(customerAddress);
        return customerAddress;
    }

    public void deleteAddress(Integer addressId){
        CustomerAddress customerAddress = addressRepository.get(addressId);
        addressRepository.delete(customerAddress);
    }

    public CustomerAddress getAddress(Integer addressId){
        return addressRepository.get(addressId);
    }

    public List<CustomerAddress> getAllAddresses(Integer customerId){
        return addressRepository.findByCustomer(customerId);
    }

    public CreditCard saveCreditCard(CreditCard creditCard){
        creditCardRepository.save(creditCard);
        return creditCard;
    }

    public void deleteCreditCard(Integer cardId){
        CreditCard creditCard = creditCardRepository.get(cardId);
        creditCardRepository.delete(creditCard);
    }

    public CreditCard getCreditCard(Integer cardId){
        return creditCardRepository.get(cardId);
    }

    public List<CreditCard> getAllCreditCards(Integer customerId){
        return creditCardRepository.findByCustomer(customerId);
    }
}
