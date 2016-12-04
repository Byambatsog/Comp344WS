package com.comp344.ecommerce.service.workflow;

import com.comp344.ecommerce.business.CustomerManager;
import com.comp344.ecommerce.business.LoginManager;
import com.comp344.ecommerce.business.OrderManager;
import com.comp344.ecommerce.domain.*;
import com.comp344.ecommerce.exception.LoginRegistrationException;
import com.comp344.ecommerce.exception.ResourceNotFoundException;
import com.comp344.ecommerce.jwt.AuthorizationUtil;
import com.comp344.ecommerce.service.representation.*;
import com.comp344.ecommerce.utils.ListPage;
import com.comp344.ecommerce.utils.Page;
import com.comp344.ecommerce.utils.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class CustomerActivity {

    @Autowired
    private CustomerManager customerManager;

    @Autowired
    private OrderManager orderManager;

    @Autowired
    private LoginManager loginManager;

    @Autowired
    private AuthorizationUtil authorizationUtil;

    public CustomerRepresentation getCustomer(Integer customerId) throws Exception {
        Customer customer = customerManager.get(customerId);
        authorizationUtil.authorize(customer);
        CustomerRepresentation customerRepresentation = new CustomerRepresentation(customer);
        setLinks(customerRepresentation, customer);
        return customerRepresentation;
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
            CustomerRepresentation customerRepresentation = new CustomerRepresentation(customer);
            setLink(customerRepresentation, customer);
            customerRepresentations.add(customerRepresentation);
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
        login.setRole(LoginRole.ROLE_CUSTOMER);
        login.setCreatedAt(new Date());

        Customer customer = new Customer();
        customer.setLogin(login);
        customer.setFirstName(customerCreateRequest.getFirstName());
        customer.setLastName(customerCreateRequest.getLastName());
        customer.setEmail(customerCreateRequest.getEmail());
        customer.setCreatedAt(new Date());
        customerManager.create(customer);

        CustomerRepresentation customerRepresentation = new CustomerRepresentation(customer);
        setLinks(customerRepresentation, customer);
        return customerRepresentation;
    }

    public void updateCustomer(Integer customerId, CustomerRequest customerRequest) throws Exception {
        Customer customer = customerManager.get(customerId);
        authorizationUtil.authorize(customer);
        customer.setFirstName(customerRequest.getFirstName());
        customer.setLastName(customerRequest.getLastName());
        customer.setEmail(customerRequest.getEmail());
        customerManager.update(customer);
    }

    public void deleteCustomer(Integer customerId) throws Exception {
        Customer customer = customerManager.get(customerId);
        authorizationUtil.authorize(customer);
        customerManager.delete(customerId);
    }

    public List<CreditCardRepresentation> getAllCreditCards(Integer customerId) throws Exception{

        Customer customer = customerManager.get(customerId);
        authorizationUtil.authorize(customer);
        List<CreditCard> cards = customerManager.getAllCreditCards(customerId);
        List<CreditCardRepresentation> cardRepresentations = new ArrayList<CreditCardRepresentation>();
        for(CreditCard card : cards){
            CreditCardRepresentation creditCardRep = new CreditCardRepresentation(card);
            setLinks(creditCardRep, card);
            cardRepresentations.add(creditCardRep);
        }
        return cardRepresentations;
    }

    public CreditCardRepresentation createCreditCard(Integer customerId, CreditCardRequest cardRequest) throws Exception {

        Customer customer = customerManager.get(customerId);
        authorizationUtil.authorize(customer);
        CreditCard card = new CreditCard();
        card.setCardNumber(cardRequest.getCardNumber());
        card.setCardType(cardRequest.getCardType());
        card.setExpireMonth(cardRequest.getExpireMonth());
        card.setExpireYear(cardRequest.getExpireYear());
        card.setNameOnCard(cardRequest.getNameOnCard());
        card.setSecurityCode(cardRequest.getSecurityCode());
        card.setCustomer(customer);
        card.setCreatedAt(new Date());
        customerManager.saveCreditCard(card);
        CreditCardRepresentation cardRep = new CreditCardRepresentation(card);
        setLinks(cardRep, card);
        return cardRep;
    }

    public void updateCreditCard(Integer id, CreditCardRequest cardRequest) throws Exception {

        CreditCard card = customerManager.getCreditCard(id);
        authorizationUtil.authorize(card.getCustomer());
        card.setCardNumber(cardRequest.getCardNumber());
        card.setCardType(cardRequest.getCardType());
        card.setExpireMonth(cardRequest.getExpireMonth());
        card.setExpireYear(cardRequest.getExpireYear());
        card.setNameOnCard(cardRequest.getNameOnCard());
        card.setSecurityCode(cardRequest.getSecurityCode());
        customerManager.saveCreditCard(card);
    }

    public CreditCardRepresentation getCreditCard(Integer id) throws Exception {
        CreditCard card = customerManager.getCreditCard(id);
        if(card == null) {
            throw new ResourceNotFoundException("No credit card found with id " + id);
        }
        authorizationUtil.authorize(card.getCustomer());
        CreditCardRepresentation cardRep = new CreditCardRepresentation(card);
        setLinks(cardRep, card);
        return cardRep;
    }

    public void deleteCreditCard(Integer id) throws Exception {
        CreditCard card = customerManager.getCreditCard(id);
        if(card == null) {
            throw new ResourceNotFoundException("No credit card found with id " + id);
        }
        authorizationUtil.authorize(card.getCustomer());
        customerManager.deleteCreditCard(id);
    }

    public List<AddressRepresentation> getAllAddresses(Integer customerId) throws Exception{

        Customer customer = customerManager.get(customerId);
        authorizationUtil.authorize(customer);
        List<CustomerAddress> addresses = customerManager.getAllAddresses(customerId);
        List<AddressRepresentation> addressRepresentations = new ArrayList<AddressRepresentation>();
        for(CustomerAddress address : addresses){
            AddressRepresentation addressRep = new AddressRepresentation(address);
            setLinks(addressRep, address);
            addressRepresentations.add(addressRep);
        }
        return addressRepresentations;
    }

    public AddressRepresentation createAddress(Integer customerId, AddressRequest addressRequest) throws Exception {

        Customer customer = customerManager.get(customerId);
        authorizationUtil.authorize(customer);
        CustomerAddress address = new CustomerAddress();
        address.setStreet(addressRequest.getStreet());
        address.setCity(addressRequest.getCity());
        address.setState(addressRequest.getState());
        address.setZipCode(addressRequest.getZipCode());
        address.setCountry(addressRequest.getCountry());
        address.setPhone(addressRequest.getPhone());
        address.setCustomer(customer);
        address.setCreatedAt(new Date());
        customerManager.saveAddress(address);
        AddressRepresentation addressRep = new AddressRepresentation(address);
        setLinks(addressRep, address);
        return addressRep;
    }

    public void updateAddress(Integer id, AddressRequest addressRequest) throws Exception {

        CustomerAddress address = customerManager.getAddress(id);
        authorizationUtil.authorize(address.getCustomer());
        address.setStreet(addressRequest.getStreet());
        address.setCity(addressRequest.getCity());
        address.setState(addressRequest.getState());
        address.setZipCode(addressRequest.getZipCode());
        address.setCountry(addressRequest.getCountry());
        address.setPhone(addressRequest.getPhone());
        customerManager.saveAddress(address);
    }

    public AddressRepresentation getAddress(Integer id) throws Exception {
        CustomerAddress address = customerManager.getAddress(id);
        if(address == null) {
            throw new ResourceNotFoundException("No customer address found with id " + id);
        }
        authorizationUtil.authorize(address.getCustomer());
        AddressRepresentation addressRep = new AddressRepresentation(address);
        setLinks(addressRep, address);
        return addressRep;
    }

    public void deleteAddress(Integer id) throws Exception {
        CustomerAddress address = customerManager.getAddress(id);
        if(address == null) {
            throw new ResourceNotFoundException("No customer address found with id " + id);
        }
        authorizationUtil.authorize(address.getCustomer());
        customerManager.deleteAddress(id);
    }

    public List<OrderRepresentation> getCustomerOrders(Integer customerId) throws Exception {

        Customer customer = customerManager.get(customerId);
        authorizationUtil.authorize(customer);
        List<Order> orders = orderManager.find(customerId, null, null, null, 0, 0).getElements();
        List<OrderRepresentation> orderRepresentations = new ArrayList<OrderRepresentation>();
        for(Order order : orders){
            orderRepresentations.add(new OrderRepresentation(order, Boolean.FALSE, Boolean.FALSE));
        }
        return orderRepresentations;
    }

    private void setLink(CustomerRepresentation customerRepresentation, Customer customer) {
        customerRepresentation.addLink(BaseRepresentation.BASE_URI + "/customerservice/customers/" + customer.getId(),
                "self", HttpMethod.GET, "");
    }

    private void setLinks(CustomerRepresentation customerRepresentation, Customer customer) {
        customerRepresentation.addLink(BaseRepresentation.BASE_URI + "/customerservice/customers/" + customer.getId(),
                "self", HttpMethod.GET, "");
        customerRepresentation.addLink(BaseRepresentation.BASE_URI + "/customerservice/customers/" + customer.getId(),
                "update", HttpMethod.PUT, "application/json");
        customerRepresentation.addLink(BaseRepresentation.BASE_URI + "/customerservice/customers/" + customer.getId(),
                "delete", HttpMethod.DELETE, "");
        customerRepresentation.addLink(BaseRepresentation.BASE_URI + "/customerservice/customers/" + customer.getId() + "/addresses",
                "addresses", HttpMethod.GET, "");
        customerRepresentation.addLink(BaseRepresentation.BASE_URI + "/customerservice/customers/" + customer.getId() + "/addresses",
                "addresses.create", HttpMethod.POST, "application/json");
        customerRepresentation.addLink(BaseRepresentation.BASE_URI + "/customerservice/customers/" + customer.getId() + "/creditcards",
                "creditcards", HttpMethod.GET, "");
        customerRepresentation.addLink(BaseRepresentation.BASE_URI + "/customerservice/customers/" + customer.getId() + "/creditcards",
                "creditcards.create", HttpMethod.POST, "application/json");
        customerRepresentation.addLink(BaseRepresentation.BASE_URI + "/customerservice/customers/" + customer.getId() + "/orders",
                "orders", HttpMethod.GET, "");
    }

    private void setLinks(CreditCardRepresentation creditCardRepresentation, CreditCard creditCard) {
        creditCardRepresentation.addLink(BaseRepresentation.BASE_URI + "/customerservice/creditcards/" + creditCard.getId(),
                "self", HttpMethod.GET, "");
        creditCardRepresentation.addLink(BaseRepresentation.BASE_URI + "/customerservice/creditcards/" + creditCard.getId(),
                "update", HttpMethod.PUT, "application/json");
        creditCardRepresentation.addLink(BaseRepresentation.BASE_URI + "/customerservice/creditcards/" + creditCard.getId(),
                "delete", HttpMethod.DELETE, "");
    }

    private void setLinks(AddressRepresentation addressRepresentation, CustomerAddress address) {
        addressRepresentation.addLink(BaseRepresentation.BASE_URI + "/customerservice/addresses/" + address.getId(),
                "self", HttpMethod.GET, "");
        addressRepresentation.addLink(BaseRepresentation.BASE_URI + "/customerservice/addresses/" + address.getId(),
                "update", HttpMethod.PUT, "application/json");
        addressRepresentation.addLink(BaseRepresentation.BASE_URI + "/customerservice/addresses/" + address.getId(),
                "delete", HttpMethod.DELETE, "");
    }
}
