package com.comp344.ecommerce.jwt;

import com.comp344.ecommerce.business.CustomerManager;
import com.comp344.ecommerce.business.LoginManager;
import com.comp344.ecommerce.business.PartnerManager;
import com.comp344.ecommerce.domain.Customer;
import com.comp344.ecommerce.domain.Login;
import com.comp344.ecommerce.domain.Partner;
import com.comp344.ecommerce.exception.UnAuthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Created by Byambatsog on 12/3/16.
 */
@Component
public class AuthorizationUtil {

    @Autowired
    private LoginManager loginManager;

    @Autowired
    private CustomerManager customerManager;

    @Autowired
    private PartnerManager partnerManager;

    public boolean authorize(Customer customer) throws UnAuthorizedException {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(customer == null || !customer.getLogin().getEmail().equals(principal)){
            throw new UnAuthorizedException("Unauthorized");
        }
        return true;
    }

    public boolean authorize(Partner partner) throws UnAuthorizedException {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(partner == null || !partner.getLogin().getEmail().equals(principal)){
            throw new UnAuthorizedException("Unauthorized");
        }
        return true;
    }

    public Customer getAuthenticatedCustomer(){
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Login login = loginManager.findByEmail(principal);
        return customerManager.findByLogin(login.getId());
    }

    public Partner getAuthenticatedPartner(){
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Login login = loginManager.findByEmail(principal);
        return partnerManager.findByLogin(login.getId());
    }
}
