package com.comp344.ecommerce.service.representation;

import com.comp344.ecommerce.domain.Login;
import com.comp344.ecommerce.domain.Partner;
import com.comp344.ecommerce.domain.PartnerType;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Byambatsog on 10/29/16.
 */
public class PartnerRepresentation {

    private Integer id;
    private String companyName;
    private String firstName;
    private String lastName;
    private String type;
    private String phone;
    private String email;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String createdAt;

    public PartnerRepresentation(){}

    public PartnerRepresentation(Partner partner){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        this.id = partner.getId();
        this.companyName = partner.getCompanyName() != null ? partner.getCompanyName() : "";
        this.firstName = partner.getFirstName() != null ? partner.getFirstName() : "";
        this.lastName = partner.getLastName() != null ? partner.getLastName() : "";
        this.type = partner.getType() != null ? partner.getType().name() : "";
        this.phone = partner.getPhone() != null ? partner.getPhone() : "";
        this.email = partner.getEmail() != null ? partner.getEmail() : "";
        this.street = partner.getStreet() != null ? partner.getStreet() : "";
        this.city = partner.getCity() != null ? partner.getCity() : "";
        this.state = partner.getState() != null ? partner.getState() : "";
        this.zipCode = partner.getZipCode() != null ? partner.getZipCode() : "";
        this.country = partner.getCountry() != null ? partner.getCountry() : "";
        this.createdAt = partner.getCreatedAt() != null ? dateFormat.format(partner.getCreatedAt()) : "";
    }

    public Integer getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getType() {
        return type;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCountry() {
        return country;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
