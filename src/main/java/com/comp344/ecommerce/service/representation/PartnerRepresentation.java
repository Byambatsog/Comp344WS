package com.comp344.ecommerce.service.representation;

import com.comp344.ecommerce.domain.Login;
import com.comp344.ecommerce.domain.Partner;
import com.comp344.ecommerce.domain.PartnerType;

import java.util.Date;

/**
 * Created by Byambatsog on 10/29/16.
 */
public class PartnerRepresentation {

    private Integer id;
    private String companyName;
    private String firstName;
    private String lastName;
    private PartnerType type;
    private String phone;
    private String email;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;

    public PartnerRepresentation(){}

    public PartnerRepresentation(Partner partner){
        this.id = partner.getId();
        this.companyName = partner.getCompanyName();
        this.firstName = partner.getFirstName();
        this.lastName = partner.getLastName();
        this.type = partner.getType();
        this.phone = partner.getPhone();
        this.email = partner.getEmail();
        this.street = partner.getStreet();
        this.city = partner.getCity();
        this.state = partner.getState();
        this.zipCode = partner.getZipCode();
        this.country = partner.getCountry();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public PartnerType getType() {
        return type;
    }

    public void setType(PartnerType type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
