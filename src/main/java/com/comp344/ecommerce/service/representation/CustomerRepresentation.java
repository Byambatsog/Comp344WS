package com.comp344.ecommerce.service.representation;

import com.comp344.ecommerce.domain.Customer;

import java.text.SimpleDateFormat;

/**
 * Created by Byambatsog on 10/4/16.
 */
public class CustomerRepresentation {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String createdAt;

    public CustomerRepresentation(){}

    public CustomerRepresentation(Customer customer){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        this.id = customer.getId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.email = customer.getEmail();
        this.createdAt = dateFormat.format(customer.getCreatedAt());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
