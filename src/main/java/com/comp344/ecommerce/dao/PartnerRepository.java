package com.comp344.ecommerce.dao;

import com.comp344.ecommerce.domain.Partner;
import com.comp344.ecommerce.domain.PartnerType;
import com.comp344.ecommerce.utils.Page;

/**
 * Created by Byambatsog on 10/1/16.
 */
public interface PartnerRepository extends BaseRepo<Partner>  {

    public void save(Partner partner);

    public Partner get(Integer id);

    public void delete(Partner partner);

    public Page<Partner> list(String companyName, String firstName, String lastName, PartnerType type, String phone,
                              String email, String city, String state, String zipCode, String country, String orderBy, int page, int size);
}
