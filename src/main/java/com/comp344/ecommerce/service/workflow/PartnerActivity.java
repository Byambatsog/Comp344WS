package com.comp344.ecommerce.service.workflow;

import com.comp344.ecommerce.business.LoginManager;
import com.comp344.ecommerce.business.PartnerManager;
import com.comp344.ecommerce.domain.Partner;
import com.comp344.ecommerce.exception.ResourceNotFoundException;
import com.comp344.ecommerce.service.representation.PartnerRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Byambatsog on 10/29/16.
 */
@Component
public class PartnerActivity {

    @Autowired
    private PartnerManager partnerManager;

    @Autowired
    private LoginManager loginManager;

    public PartnerRepresentation getPartner(Integer id) throws Exception {
        Partner partner = partnerManager.get(id);
        if(partner == null) {
            throw new ResourceNotFoundException("No partner found with id " + id);
        }
        return new PartnerRepresentation(partner);
    }
}
