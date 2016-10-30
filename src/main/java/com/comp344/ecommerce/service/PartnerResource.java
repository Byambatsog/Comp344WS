package com.comp344.ecommerce.service;

import com.comp344.ecommerce.business.PartnerManager;
import com.comp344.ecommerce.domain.Login;
import com.comp344.ecommerce.domain.Partner;
import com.comp344.ecommerce.domain.PartnerType;
import com.comp344.ecommerce.service.representation.PartnerRepresentation;
import com.comp344.ecommerce.service.workflow.PartnerActivity;
import com.comp344.ecommerce.utils.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Byambatsog on 10/2/16.
 */
@Controller
@RequestMapping("/partnerservice")
public class PartnerResource {

    @Autowired
    private PartnerActivity partnerActivity;

    @RequestMapping(value = "/partner/{id}", method = RequestMethod.GET)
    @ResponseBody
    public PartnerRepresentation get(@PathVariable(value = "id") Integer id) throws Exception {
        return partnerActivity.getPartner(id);
    }
}