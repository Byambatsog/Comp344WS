package com.comp344.ecommerce.service.representation;

import com.comp344.ecommerce.domain.Partner;

import java.text.SimpleDateFormat;

/**
 * Created by Byambatsog on 11/26/16.
 */
public class ProductPartnerRepresentation extends BaseRepresentation {

    private Integer id;
    private String companyName;
    private String firstName;
    private String lastName;
    private String type;
    private String phone;
    private String email;

    public ProductPartnerRepresentation(){}

    public ProductPartnerRepresentation(Partner partner){

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        this.id = partner.getId();
        this.companyName = partner.getCompanyName() != null ? partner.getCompanyName() : "";
        this.firstName = partner.getFirstName() != null ? partner.getFirstName() : "";
        this.lastName = partner.getLastName() != null ? partner.getLastName() : "";
        this.type = partner.getType() != null ? partner.getType().name() : "";
        this.phone = partner.getPhone() != null ? partner.getPhone() : "";
        this.email = partner.getEmail() != null ? partner.getEmail() : "";
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

}
