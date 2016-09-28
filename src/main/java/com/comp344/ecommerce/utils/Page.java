package com.comp344.ecommerce.utils;

import java.util.List;

/**
 * Created by Byambatsog on 9/27/16.
 */
public interface Page<E> {

    public final static int FIRST_PAGE=1;

    boolean isFirstPage();

    boolean isLastPage();

    boolean hasNextPage();

    boolean hasPreviousPage();

    int getLastPageNumber();

    List<E> getThisPageElements();

    int getTotalNumberOfElements();

    int getThisPageFirstElementNumber();

    int getThisPageLastElementNumber();

    int getNextPageNumber();

    int getPreviousPageNumber();

    int getPageSize();

    int getPageNumber();
}
