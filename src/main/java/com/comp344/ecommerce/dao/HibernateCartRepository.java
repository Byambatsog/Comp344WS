package com.comp344.ecommerce.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.comp344.ecommerce.domain.Cart;

@Repository
public class HibernateCartRepository  extends HibernateBaseRepository<Cart> {
	
	@Autowired
    public HibernateCartRepository(SessionFactory sessionFactory) {
        super(Cart.class);
        super.setSessionFactory(sessionFactory);
    }

    public List<Cart> findByCustomer(Integer customerId, Boolean status){
        return getHibernateTemplate().find("from Cart where customers_id=? and status=?", new Object[]{customerId, status});
    }

}
