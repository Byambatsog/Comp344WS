package com.comp344.ecommerce.dao;

import com.comp344.ecommerce.domain.Product;
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
public class HibernateProductRepository extends HibernateBaseRepository<Product> {

    @Autowired
    public HibernateProductRepository(SessionFactory sessionFactory) {
        super(Product.class);
        super.setSessionFactory(sessionFactory);
    }

    public Page<Product> list(String name, Integer categoryId, Integer partnerId, String brandName, Boolean status,
                              String orderBy, int page, int size){

        String where = "";
        String conn = " where ";

        if (orderBy==null){
            orderBy = "order by created_at desc";
        }

        List params=new ArrayList();

        if(name != null && !name.equals("")){
            where+=conn + "name like ?";
            conn = " and ";
            params.add("%" + name + "%");
        }

        if(categoryId != null){
            where+=conn + "product_categories_id=?";
            conn = " and ";
            params.add(categoryId);
        }

        if(partnerId != null){
            where+=conn + "partners_id=?";
            conn = " and ";
            params.add(partnerId);
        }

        if(brandName != null && !brandName.equals("")){
            where+=conn + "brandName like ?";
            conn = " and ";
            params.add("%" + brandName + "%");
        }

        if(status != null){
            where+=conn + "status=?";
            conn = " and ";
            params.add(status);
        }

        Page result;
        if(page>0&&size>0){
            List l =getResult("from Product" + where + " " + orderBy,params.toArray(),page,size);
            int count =getResultTotalSize("select count(*) from Product" + where,params.toArray());
            result = new ListPage(l, page, size, count);
        } else {
            List l = getHibernateTemplate().find("from Product" + where + " " + orderBy,params.toArray());
            result=new ListPage(l,1,l.size(),l.size());
        }
        return result;
    }
}
