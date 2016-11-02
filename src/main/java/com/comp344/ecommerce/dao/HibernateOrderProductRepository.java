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

    public List<OrderProduct> find(Integer orderId, Integer productId, Integer partnerId, OrderProductStatus status, String orderBy){

        String where = "";
        String conn = " where ";

        if (orderBy==null){
            orderBy = "order by op.product.id desc";
        }

        List params=new ArrayList();

        if(orderId != null){
            where+=conn + "op.order.id=?";
            conn = " and ";
            params.add(orderId);
        }

        if(productId != null){
            where+=conn + "op.product.id=?";
            conn = " and ";
            params.add(productId);
        }

        if(partnerId != null){
            where+=conn + "op.product.partner.id=?";
            conn = " and ";
            params.add(partnerId);
        }

        if(status != null){
            where+=conn + "op.status=?";
            conn = " and ";
            params.add(status);
        }

        return getHibernateTemplate().find("from OrderProduct op" + where + " " + orderBy,params.toArray());
    }

}
