package com.comp344.ecommerce.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Byambatsog on 10/1/16.
 */
@Entity
@Table(name="customer_addresses")
public class CustomerAddress implements Serializable {

    private static final long serialVersionUID = 7961030590426738251L;

    private Integer id;
    private Customer customer;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String phone;
    private Date createdAt;

    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JoinColumn(name = "customers_id")
    @ManyToOne
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "zip_code")
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "created_at")
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Transient
    public String getAddress(){
        String address = "";
        if(this.street != null && !this.street.equals("")) address = address + street;
        if(this.city != null && !this.city.equals("")) address = address + ", " + city;
        if(this.state != null && !this.state.equals("")) address = address + ", " + state;
        if(this.zipCode != null && !this.zipCode.equals("")) address = address + " " + zipCode;
        if(this.country != null && !this.country.equals("")) address = address + ", " + country;
        if(this.phone != null && !this.phone.equals("")) address = address + ", " + phone;
        return address;
    }
}
