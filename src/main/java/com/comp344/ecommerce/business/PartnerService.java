package com.comp344.ecommerce.business;

import com.comp344.ecommerce.dao.HibernateLoginRepository;
import com.comp344.ecommerce.dao.HibernatePartnerRepository;
import com.comp344.ecommerce.domain.Partner;
import com.comp344.ecommerce.domain.PartnerType;
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

    public Partner create(Partner partner) throws Exception {

        loginRepository.save(partner.getLogin());
        partnerRepository.save(partner);
        return partner;
    }
}
