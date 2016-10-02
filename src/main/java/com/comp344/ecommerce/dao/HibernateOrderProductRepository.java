package com.comp344.ecommerce.dao;

import com.comp344.ecommerce.domain.OrderProduct;
import com.comp344.ecommerce.domain.OrderProductStatus;
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
public class HibernateOrderProductRepository extends HibernateBaseRepository<OrderProduct> {

    @Autowired
    public HibernateOrderProductRepository(SessionFactory sessionFactory) {
        super(OrderProduct.class);
        super.setSessionFactory(sessionFactory);
    }

    public Page<OrderProduct> list(Integer orderId, Integer productId, OrderProductStatus status, String orderBy,
                                   int page, int size){

        String where = "";
        String conn = " where ";

        if (orderBy==null){
            orderBy = "order by products_id desc";
        }

        List params=new ArrayList();

        if(orderId != null){
            where+=conn + "orders_id=?";
            conn = " and ";
            params.add(orderId);
        }

        if(productId != null){
            where+=conn + "products_id=?";
            conn = " and ";
            params.add(productId);
        }

        if(status != null){
            where+=conn + "status=?";
            conn = " and ";
            params.add(status);
        }

        Page result;
        if(page>0&&size>0){
            List l =getResult("from OrderProduct" + where + " " + orderBy,params.toArray(),page,size);
            int count =getResultTotalSize("select count(*) from OrderProduct" + where,params.toArray());
            result = new ListPage(l, page, size, count);
        } else {
            List l = getHibernateTemplate().find("from OrderProduct" + where + " " + orderBy,params.toArray());
            result=new ListPage(l,1,l.size(),l.size());
        }
        return result;
    }

}
