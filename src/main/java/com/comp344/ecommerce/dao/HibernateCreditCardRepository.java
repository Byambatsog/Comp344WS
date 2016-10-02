package com.comp344.ecommerce.dao;

import com.comp344.ecommerce.domain.CreditCard;
import com.comp344.ecommerce.domain.Customer;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Byambatsog on 10/2/16.
 */
@Repository
public class HibernateCreditCardRepository extends HibernateBaseRepository<CreditCard> {

    @Autowired
    public HibernateCreditCardRepository(SessionFactory sessionFactory) {
        super(CreditCard.class);
        super.setSessionFactory(sessionFactory);
    }

    public List<CreditCard> findByCustomer(Customer customer){
        return getHibernateTemplate().find("from CreditCard where customers_id=?", new Object[]{customer.getId()});
    }
}
