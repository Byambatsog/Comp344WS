package com.comp344.ecommerce.service;

import com.comp344.ecommerce.dao.HibernateCustomerRepository;
import com.comp344.ecommerce.dao.HibernatePartnerRepository;
import com.comp344.ecommerce.domain.Partner;
import com.comp344.ecommerce.domain.PartnerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by Byambatsog on 10/1/16.
 */
@Controller("partnerService")
@RequestMapping("/partner")
public class PartnerService {

    @Autowired
    private HibernatePartnerRepository repository;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    public Partner create() throws Exception {

        Partner partner = new Partner();
        partner.setCompanyName("Brilent, Inc");
        partner.setFirstName("Byambatsog");
        partner.setLastName("Chimed");
        partner.setType(PartnerType.COMPANY);
        partner.setPhone("7731238743");
        partner.setEmail("byambatsog@gmail.com");
        partner.setStreet("25 East Pearson");
        partner.setCity("Chicago");
        partner.setState("IL");
        partner.setZipCode("60213");
        partner.setCountry("United States");
        partner.setCreatedAt(new Date());
        repository.save(partner);
        return partner;
    }
}
