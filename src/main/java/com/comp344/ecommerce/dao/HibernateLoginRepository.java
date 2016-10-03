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
 * Created by Byambatsog on 10/2/16.
 */
@Repository
public class HibernateLoginRepository extends HibernateBaseRepository<Login> {

    @Autowired
    public HibernateLoginRepository(SessionFactory sessionFactory) {
        super(Login.class);
        super.setSessionFactory(sessionFactory);
    }

    public Login findByUserName(String userName){
        List list = getHibernateTemplate().find("from Login where username=?", new Object[]{userName});
        if(list.isEmpty()) return null;
        return (Login)list.get(0);
    }

    public Login findByEmail(String email){
        List list = getHibernateTemplate().find("from Login where email=?", new Object[]{email});
        if(list.isEmpty()) return null;
        return (Login)list.get(0);
    }

    public Page<Login> find(String email, String username, Boolean active, Boolean admin, String orderBy,
                            int page, int size){

        String where = "";
        String conn = " where ";

        if (orderBy==null){
            orderBy = "order by created_at desc";
        }

        List params=new ArrayList();

        if(email != null && !email.equals("")){
            where+=conn + "email like ?";
            conn = " and ";
            params.add("%" + email + "%");
        }

        if(username != null && !username.equals("")){
            where+=conn + "username like ?";
            conn = " and ";
            params.add("%" + username + "%");
        }

        if(active != null){
            where+=conn + "active=?";
            conn = " and ";
            params.add(active);
        }

        if(admin != null){
            where+=conn + "admin=?";
            conn = " and ";
            params.add(admin);
        }

        Page result;
        if(page>0&&size>0){
            List l =getResult("from Login" + where + " " + orderBy,params.toArray(),page,size);
            int count =getResultTotalSize("select count(*) from Login" + where,params.toArray());
            result = new ListPage(l, page, size, count);
        } else {
            List l = getHibernateTemplate().find("from Login" + where + " " + orderBy,params.toArray());
            result=new ListPage(l,1,l.size(),l.size());
        }
        return result;
    }
}
