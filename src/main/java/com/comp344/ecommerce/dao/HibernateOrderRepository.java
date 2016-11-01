package com.comp344.ecommerce.dao;

import com.comp344.ecommerce.domain.Order;
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
public class HibernateOrderRepository extends HibernateBaseRepository<Order> {

    @Autowired
    public HibernateOrderRepository(SessionFactory sessionFactory) {
        super(Order.class);
        super.setSessionFactory(sessionFactory);
    }

    public Page<Order> find(Integer customerId, Integer shippingAddresssId, Integer billingAddressId,
                            String orderBy, int page, int size){

        String where = "";
        String conn = " where ";

        if (orderBy==null){
            orderBy = "order by created_at desc";
        }

        List params=new ArrayList();

        if(customerId != null){
            where+=conn + "customers_id=?";
            conn = " and ";
            params.add(customerId);
        }

        if(shippingAddresssId != null){
            where+=conn + "shipping_addresses_id=?";
            conn = " and ";
            params.add(shippingAddresssId);
        }

        if(billingAddressId != null){
            where+=conn + "billing_addresses_id=?";
            conn = " and ";
            params.add(billingAddressId);
        }

        Page result;
        if(page>0&&size>0){
            List l =getResult("from Order" + where + " " + orderBy,params.toArray(),page,size);
            int count =getResultTotalSize("select count(*) from Order" + where,params.toArray());
            result = new ListPage(l, page, size, count);
        } else {
            List l = getHibernateTemplate().find("from Order" + where + " " + orderBy,params.toArray());
            result=new ListPage(l,1,l.size(),l.size());
        }
        return result;
    }
}
