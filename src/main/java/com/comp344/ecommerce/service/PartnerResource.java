package com.comp344.ecommerce.service;

import com.comp344.ecommerce.business.PartnerService;
import com.comp344.ecommerce.domain.Login;
import com.comp344.ecommerce.domain.Partner;
import com.comp344.ecommerce.domain.PartnerType;
import com.comp344.ecommerce.utils.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by Byambatsog on 10/2/16.
 */
@Controller
@RequestMapping("/partner")
public class PartnerResource {

    @Autowired
    private PartnerService partnerService;

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

        Login login = new Login();
        login.setEmail("bchimed@luc.edu");
        login.setUsername("byambatsog");
        PasswordEncoder passwordEncoder = new PasswordEncoder();
        login.setPassword(passwordEncoder.encode("123456789"));
        login.setActive(true);
        login.setAdmin(true);
        login.setCreatedAt(new Date());
        partner.setLogin(login);

        partnerService.create(partner);
        return partner;
    }
}