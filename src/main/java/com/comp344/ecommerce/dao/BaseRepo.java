package com.comp344.ecommerce.dao;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: erdenetsogt
 * Date: 12/19/13
 * Time: 2:14 PM
 * To change this template use File | Settings | File Templates.
 */

public interface BaseRepo<I> {
    public void save(I item);
    public I get(Integer id);
    public void delete(I i);
    public List<I> list();
}
