package com.comp344.ecommerce.service.representation;

import com.comp344.ecommerce.domain.CustomerAddress;

/**
 * Created by Byambatsog on 10/31/16.
 */
public class AddressRepresentation extends BaseRepresentation {

    private Integer id;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private String phone;

    public AddressRepresentation(){}

    public AddressRepresentation(CustomerAddress address){
        this.id = address.getId();
        this.street = address.getStreet();
        this.city = address.getCity();
        this.state = address.getState();
        this.zipCode = address.getZipCode();
        this.country = address.getCountry() != null ? address.getCountry() : "";
        this.phone = address.getPhone() != null ? address.getPhone() : "";
    }

    public Integer getId() {
        return id;
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

    public String getPhone() {
        return phone;
    }
}
