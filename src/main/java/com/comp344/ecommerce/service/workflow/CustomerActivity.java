package com.comp344.ecommerce.service.workflow;

import com.comp344.ecommerce.business.CustomerManager;
import com.comp344.ecommerce.business.LoginManager;
import com.comp344.ecommerce.domain.CreditCard;
import com.comp344.ecommerce.domain.Customer;
import com.comp344.ecommerce.domain.Login;
import com.comp344.ecommerce.exception.LoginRegistrationException;
import com.comp344.ecommerce.exception.ResourceNotFoundException;
import com.comp344.ecommerce.service.representation.*;
import com.comp344.ecommerce.utils.ListPage;
import com.comp344.ecommerce.utils.Page;
import com.comp344.ecommerce.utils.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CustomerActivity {

    @Autowired
    private CustomerManager customerManager;

    @Autowired
    private LoginManager loginManager;

    public CustomerRepresentation getCustomer(Integer id) throws Exception {
        Customer customer = customerManager.get(id);
        if(customer == null) {
            throw new ResourceNotFoundException("No customer found with id " + id);
        }
        return new CustomerRepresentation(customer);
    }

    public Page<CustomerRepresentation> findCustomers(String searchQuery, String orderBy, int page, int pageSize) throws Exception{

        if(orderBy != null && !orderBy.equals("")){
            if (orderBy.equals("created")){
                orderBy = "order by created_at desc";
            } else if (orderBy.equals("name")){
                orderBy = "order by first_name asc, last_name asc";
            }
        }

        Page<Customer> list = customerManager.find(searchQuery, orderBy, page, pageSize);
        List<CustomerRepresentation> customerRepresentations = new ArrayList<CustomerRepresentation>();
        for(Customer customer : list.getElements()){
            customerRepresentations.add(new CustomerRepresentation(customer));
        }
        Page<CustomerRepresentation> customerPage = new ListPage<CustomerRepresentation>(customerRepresentations, list.getPageNumber(), list.getPageSize(), list.getTotalNumberOfElements());
        return customerPage;
    }

    public CustomerRepresentation createCustomer(CustomerCreateRequest customerCreateRequest) throws Exception {

        Login exists = loginManager.findByEmail(customerCreateRequest.getEmail());
        if(exists != null)
            throw new LoginRegistrationException("Following email has already registered: " + customerCreateRequest.getEmail());

        PasswordEncoder passwordEncoder = new PasswordEncoder();

        Login login = new Login();
        login.setEmail(customerCreateRequest.getEmail());
        login.setPassword(passwordEncoder.encode(customerCreateRequest.getPassword()));
        login.setActive(Boolean.TRUE);
        login.setAdmin(Boolean.FALSE);
        login.setCreatedAt(new Date());

        Customer customer = new Customer();
        customer.setLogin(login);
        customer.setFirstName(customerCreateRequest.getFirstName());
        customer.setLastName(customerCreateRequest.getLastName());
        customer.setEmail(customerCreateRequest.getEmail());
        customer.setCreatedAt(new Date());
        customerManager.create(customer);

        return new CustomerRepresentation(customer);
    }

    public void updateCustomer(Integer id, CustomerRequest customerRequest) throws Exception {

        Customer customer = customerManager.get(id);
        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer.setEmail(customerRequest.getEmail());
        customerManager.update(customer);
    }

    public void deleteCustomer(Integer id) throws Exception {
        Customer customer = customerManager.get(id);
        if(customer == null) {
            throw new ResourceNotFoundException("No customer found with id " + id);
        }
        customerManager.delete(id);
    }

    public List<CreditCardRepresentation> getAllCreditCards(Integer customerId) throws Exception{

        List<CreditCard> cards = customerManager.getAllCreditCards(customerId);

        List<CreditCardRepresentation> cardRepresentations = new ArrayList<CreditCardRepresentation>();
        for(CreditCard card : cards){
            cardRepresentations.add(new CreditCardRepresentation(card));
        }
        return cardRepresentations;
    }

    public CreditCardRepresentation createCreditCard(Integer customerId, CreditCardRequest cardRequest) throws Exception {

        CreditCard card = new CreditCard();
        card.setCardNumber(cardRequest.getCardNumber());
        card.setCardType(cardRequest.getCardType());
        card.setExpireMonth(cardRequest.getExpireMonth());
        card.setExpireYear(cardRequest.getExpireYear());
        card.setNameOnCard(cardRequest.getNameOnCard());
        card.setSecurityCode(cardRequest.getSecurityCode());
        card.setCustomer(customerManager.get(customerId));
        card.setCreatedAt(new Date());
        customerManager.saveCreditCard(card);
        CreditCardRepresentation cardRep = new CreditCardRepresentation(card);
        return cardRep;
    }

    public void updateCreditCard(Integer id, CreditCardRequest cardRequest) throws Exception {

        CreditCard card = customerManager.getCreditCard(id);
        card.setCardNumber(cardRequest.getCardNumber());
        card.setCardType(cardRequest.getCardType());
        card.setExpireMonth(cardRequest.getExpireMonth());
        card.setExpireYear(cardRequest.getExpireYear());
        card.setNameOnCard(cardRequest.getNameOnCard());
        card.setSecurityCode(cardRequest.getSecurityCode());
        customerManager.saveCreditCard(card);
    }

    public CreditCardRepresentation getCreditCard(Integer id, Integer customerId) throws Exception {
        CreditCard card = customerManager.getCreditCard(id);
        if(card == null) {
            throw new ResourceNotFoundException("No credit card found with id " + id);
        } else if (!card.getCustomer().getId().equals(customerId)){
            throw new ResourceNotFoundException("No credit card found with id " + id + " for customer with id " + customerId);
        }
        return new CreditCardRepresentation(card);
    }

    public void deleteCreditCard(Integer id, Integer customerId) throws Exception {
        CreditCard card = customerManager.getCreditCard(id);
        if(card == null) {
            throw new ResourceNotFoundException("No credit card found with id " + id);
        } else if (!card.getCustomer().getId().equals(customerId)){
            throw new ResourceNotFoundException("No credit card found with id " + id + " for customer with id " + customerId);
        }
        customerManager.deleteCreditCard(id);
    }
}
