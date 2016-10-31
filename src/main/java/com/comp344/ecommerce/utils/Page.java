package com.comp344.ecommerce.utils;

import java.util.List;

/**
 * Created by Byambatsog on 9/27/16.
 */
public interface Page<E> {

    public final static int FIRST_PAGE=1;

    List<E> getElements();

    int getTotalNumberOfElements();

    int getPageSize();

    int getPageNumber();
}
