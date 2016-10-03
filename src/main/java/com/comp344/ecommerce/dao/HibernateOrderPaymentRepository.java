package com.comp344.ecommerce.dao;

import com.comp344.ecommerce.domain.OrderPayment;
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
public class HibernateOrderPaymentRepository extends HibernateBaseRepository<OrderPayment> {

    @Autowired
    public HibernateOrderPaymentRepository(SessionFactory sessionFactory) {
        super(OrderPayment.class);
        super.setSessionFactory(sessionFactory);
    }

    public Page<OrderPayment> find(Integer orderId, Integer creditCardId, String orderBy,
                                   int page, int size){

        String where = "";
        String conn = " where ";

        if (orderBy==null){
            orderBy = "order by paid_at desc";
        }

        List params=new ArrayList();

        if(orderId != null){
            where+=conn + "orders_id=?";
            conn = " and ";
            params.add(orderId);
        }

        if(creditCardId != null){
            where+=conn + "credit_cards_id=?";
            conn = " and ";
            params.add(creditCardId);
        }

        Page result;
        if(page>0&&size>0){
            List l =getResult("from OrderPayment" + where + " " + orderBy,params.toArray(),page,size);
            int count =getResultTotalSize("select count(*) from OrderPayment" + where,params.toArray());
            result = new ListPage(l, page, size, count);
        } else {
            List l = getHibernateTemplate().find("from OrderPayment" + where + " " + orderBy,params.toArray());
            result=new ListPage(l,1,l.size(),l.size());
        }
        return result;
    }
}
