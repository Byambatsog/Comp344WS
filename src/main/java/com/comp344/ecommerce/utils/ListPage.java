package com.comp344.ecommerce.utils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Byambatsog on 9/27/16.
 */
public class ListPage<E> implements Page<E>, Serializable {

    private static final long serialVersionUID = 6411977861305796799L;

    private List<E> elements;
    private int pageNumber;
    private int pageSize;
    private int totalNumberOfElements;

    public ListPage(List<E> elements, int pageNumber, int pageSize, int totalNumberOfElements){
        this.elements=elements;
        this.pageNumber=pageNumber;
        this.pageSize=pageSize;
        this.totalNumberOfElements=totalNumberOfElements;
    }

    public List<E> getElements() {
        return elements;
    }

    public int getTotalNumberOfElements() {
        return totalNumberOfElements;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }
}
