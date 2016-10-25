package com.comp344.ecommerce.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.comp344.ecommerce.domain.CartItem;


@Repository
public class HibernateCartItemRepository extends HibernateBaseRepository<CartItem> {
	
	@Autowired
    public HibernateCartItemRepository(SessionFactory sessionFactory) {
        super(CartItem.class);
        super.setSessionFactory(sessionFactory);
    }

    public List<CartItem> findByCart(Integer customerCartId){
        return getHibernateTemplate().find("from CartItem where customer_carts_id=?", new Object[]{customerCartId});
    }

}
