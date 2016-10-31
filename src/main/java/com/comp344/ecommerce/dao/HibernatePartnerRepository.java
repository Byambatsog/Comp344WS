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

    public Partner findByLogin(Integer loginId){
        List list = getHibernateTemplate().find("from Partner where login_id=?", new Object[]{loginId});
        if(list.isEmpty()) return null;
        return (Partner) list.get(0);
    }

    public Page<Partner> find(String searchQuery, PartnerType type, String orderBy, int page, int size){

        String where = "";
        String conn = " where ";

        if (orderBy==null){
            orderBy = "order by created_at desc";
        }

        List params=new ArrayList();

        if(searchQuery != null && !searchQuery.equals("")){
            where+=conn + "companyName like ? or firstName like ? or lastName like ?";
            conn = " and ";
            params.add("%" + searchQuery + "%");
            params.add("%" + searchQuery + "%");
            params.add("%" + searchQuery + "%");
        }

        if (type!=null){
            where+=conn + "type=?";
            conn = " and ";
            params.add(type);
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
