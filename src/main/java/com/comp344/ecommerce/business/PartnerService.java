package com.comp344.ecommerce.business;

import com.comp344.ecommerce.dao.HibernateLoginRepository;
import com.comp344.ecommerce.dao.HibernatePartnerRepository;
import com.comp344.ecommerce.domain.Login;
import com.comp344.ecommerce.domain.Partner;
import com.comp344.ecommerce.domain.PartnerType;
import com.comp344.ecommerce.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by Byambatsog on 10/1/16.
 */
@Service("partnerService")
public class PartnerService {

    @Autowired
    private HibernatePartnerRepository partnerRepository;

    @Autowired
    private HibernateLoginRepository loginRepository;

    public void create(Partner partner) throws Exception {
        loginRepository.save(partner.getLogin());
        partnerRepository.save(partner);
    }

    public void update(Partner partner) throws Exception {
        partnerRepository.save(partner);
    }

    public Partner get(Integer id) throws Exception {
        return partnerRepository.get(id);
    }

    public Partner findByLogin(Integer loginId){
        return partnerRepository.findByLogin(loginId);
    }

    public void delete(Integer id) throws Exception {
        Partner partner = partnerRepository.get(id);
        loginRepository.delete(partner.getLogin());
        partnerRepository.delete(partner);
    }

    public Page<Partner> find(String companyName, String firstName, String lastName, PartnerType type, String phone,
                              String email, String city, String state, String zipCode, String country, String orderBy, int page, int size){
        return partnerRepository.find(companyName, firstName, lastName, type, phone, email, city, state, zipCode, country, orderBy, page, size);
    }
}
