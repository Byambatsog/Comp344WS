package com.comp344.ecommerce.dao;

import com.comp344.ecommerce.domain.Customer;
import com.comp344.ecommerce.domain.CustomerAddress;
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
public class HibernateCustomerAddressRepository extends HibernateBaseRepository<CustomerAddress> {

    @Autowired
    public HibernateCustomerAddressRepository(SessionFactory sessionFactory) {
        super(CustomerAddress.class);
        super.setSessionFactory(sessionFactory);
    }

    public List<CustomerAddress> findByCustomer(Customer customer){
        return getHibernateTemplate().find("from CustomerAddress where customers_id=?", new Object[]{customer.getId()});
    }
}
