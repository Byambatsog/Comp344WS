package com.comp344.ecommerce.dao;

import com.comp344.ecommerce.domain.ProductCategory;
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
public class HibernateProductCategoryRepository extends HibernateBaseRepository<ProductCategory> {

    @Autowired
    public HibernateProductCategoryRepository(SessionFactory sessionFactory) {
        super(ProductCategory.class);
        super.setSessionFactory(sessionFactory);
    }

    public Page<ProductCategory> find(String name, Boolean status, String orderBy, int page, int size){

        String where = "";
        String conn = " where ";

        if (orderBy==null){
            orderBy = "order by ranking asc";
        }

        List params=new ArrayList();

        if(name != null && !name.equals("")){
            where+=conn + "name like ?";
            conn = " and ";
            params.add("%" + name + "%");
        }

        if(status != null){
            where+=conn + "status=?";
            conn = " and ";
            params.add(status);
        }

        Page result;
        if(page>0&&size>0){
            List l =getResult("from ProductCategory" + where + " " + orderBy,params.toArray(),page,size);
            int count =getResultTotalSize("select count(*) from ProductCategory" + where,params.toArray());
            result = new ListPage(l, page, size, count);
        } else {
            List l = getHibernateTemplate().find("from ProductCategory" + where + " " + orderBy,params.toArray());
            result=new ListPage(l,1,l.size(),l.size());
        }
        return result;
    }
}
