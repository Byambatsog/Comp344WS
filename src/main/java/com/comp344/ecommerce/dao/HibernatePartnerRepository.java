package com.comp344.ecommerce.dao;

import com.comp344.ecommerce.domain.Partner;
import com.comp344.ecommerce.domain.PartnerType;
import com.comp344.ecommerce.utils.ListPage;
import com.comp344.ecommerce.utils.Page;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Byambatsog on 10/1/16.
 */
@Repository
public class HibernatePartnerRepository extends HibernateBaseRepository<Partner> {

    @Autowired
    public HibernatePartnerRepository(SessionFactory sessionFactory) {
        super(Partner.class);
        super.setSessionFactory(sessionFactory);
    }

    public Page<Partner> list(String companyName, String firstName, String lastName, PartnerType type, String phone,
                              String email, String city, String state, String zipCode, String country, String orderBy, int page, int size){

        String where = "";
        String conn = " where ";

        if (orderBy==null){
            orderBy = "order by created_at desc";
        }

        List params=new ArrayList();

        if(companyName!=null&&!companyName.equals("")){
            where+=conn + "companyName like ?";
            conn = " and ";
            params.add("%" + companyName + "%");
        }

        if(firstName!=null&&!firstName.equals("")){
            where+=conn + "firstName like ?";
            conn = " and ";
            params.add("%" + firstName + "%");
        }

        if(lastName!=null&&!lastName.equals("")){
            where+=conn + "lastName like ?";
            conn = " and ";
            params.add("%" + lastName + "%");
        }

        if (type!=null){
            where+=conn + "type=?";
            conn = " and ";
            params.add(type);
        }

        if(phone!=null&&!phone.equals("")){
            where+=conn + "phone like ?";
            conn = " and ";
            params.add("%" + phone + "%");
        }

        if(email!=null&&!email.equals("")){
            where+=conn + "email like ?";
            conn = " and ";
            params.add("%" + email + "%");
        }

        if(city!=null&&!city.equals("")){
            where+=conn + "city like ?";
            conn = " and ";
            params.add("%" + city + "%");
        }

        if(state!=null&&!state.equals("")){
            where+=conn + "state=?";
            conn = " and ";
            params.add(state);
        }

        if(zipCode!=null&&!zipCode.equals("")){
            where+=conn + "zipCode=?";
            conn = " and ";
            params.add(zipCode);
        }

        if(country!=null&&!country.equals("")){
            where+=conn + "country like ?";
            conn = " and ";
            params.add("%" + country + "%");
        }

        Page result;
        if(page>0&&size>0){
            List l =getResult("from Partner" + where + " " + orderBy,params.toArray(),page,size);
            int count =getResultTotalSize("select count(*) from Partner" + where,params.toArray());
            result = new ListPage(l, page, size, count);
        } else {
            List l = getHibernateTemplate().find("from Partner" + where + " " + orderBy,params.toArray());
            result=new ListPage(l,1,l.size(),l.size());
        }
        return result;
    }
}
