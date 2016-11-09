package com.comp344.ecommerce.dao;

import com.comp344.ecommerce.domain.Customer;
import com.comp344.ecommerce.domain.Login;
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

    public Customer findByLogin(Integer loginId){
        List list = getHibernateTemplate().find("from Login where login_id=?", new Object[]{loginId});
        if(list.isEmpty()) return null;
        return (Customer) list.get(0);
    }

    public Page<Customer> find(String searchQuery, String orderBy, int page, int size){

        String where = "";
        String conn = " where ";

        if (orderBy==null){
            orderBy = "order by created_at desc";
        }

        List params=new ArrayList();

        if(searchQuery!=null&&!searchQuery.equals("")){
            where+=conn + "firstName like ? or lastName like ? or email like ?";
            conn = " and ";
            params.add("%" + searchQuery + "%");
            params.add("%" + searchQuery + "%");
            params.add("%" + searchQuery + "%");
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
