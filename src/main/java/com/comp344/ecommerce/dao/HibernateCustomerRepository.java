package com.comp344.ecommerce.dao;

import com.comp344.ecommerce.domain.Customer;
import com.comp344.ecommerce.utils.ListPage;
import com.comp344.ecommerce.utils.Page;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Byambatsog on 9/27/16.
 */
@Repository
public class HibernateCustomerRepository extends HibernateBaseRepository<Customer> {

    @Autowired
    public HibernateCustomerRepository(SessionFactory sessionFactory) {
        super(Customer.class);
        super.setSessionFactory(sessionFactory);
    }

    public Page<Customer> list(String firstName, String lastName, String email, String orderBy, int page, int size){

        String where = "";
        String conn = " where ";

        if (orderBy==null){
            orderBy = "order by created_at desc";
        }

        List params=new ArrayList();

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

        if(email!=null&&!email.equals("")){
            where+=conn + "email like ?";
            conn = " and ";
            params.add("%" + email + "%");
        }

        Page result;
        if(page>0&&size>0){
            List l =getResult("from Customer" + where + " " + orderBy,params.toArray(),page,size);
            int count =getResultTotalSize("select count(*) from Customer" + where,params.toArray());
            result = new ListPage(l, page, size, count);
        } else {
            List l = getHibernateTemplate().find("from Customer" + where + " " + orderBy,params.toArray());
            result=new ListPage(l,1,l.size(),l.size());
        }
        return result;
    }
}
