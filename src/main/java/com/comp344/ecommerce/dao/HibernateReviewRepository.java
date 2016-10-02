package com.comp344.ecommerce.dao;

import com.comp344.ecommerce.domain.Review;
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
public class HibernateReviewRepository extends HibernateBaseRepository<Review> {

    @Autowired
    public HibernateReviewRepository(SessionFactory sessionFactory) {
        super(Review.class);
        super.setSessionFactory(sessionFactory);
    }

    public Page<Review> list(Integer productId, Integer customerId, String orderBy, int page, int size){

        String where = "";
        String conn = " where ";

        if (orderBy==null){
            orderBy = "order by created_at desc";
        }

        List params=new ArrayList();

        if(productId != null){
            where+=conn + "products_id=?";
            conn = " and ";
            params.add(productId);
        }

        if(customerId != null){
            where+=conn + "customers_id=?";
            conn = " and ";
            params.add(customerId);
        }

        Page result;
        if(page>0&&size>0){
            List l =getResult("from Review" + where + " " + orderBy,params.toArray(),page,size);
            int count =getResultTotalSize("select count(*) from Review" + where,params.toArray());
            result = new ListPage(l, page, size, count);
        } else {
            List l = getHibernateTemplate().find("from Review" + where + " " + orderBy,params.toArray());
            result=new ListPage(l,1,l.size(),l.size());
        }
        return result;
    }
}
