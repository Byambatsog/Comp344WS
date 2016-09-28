package com.comp344.ecommerce.utils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Byambatsog on 9/27/16.
 */
public class ListPage<E> implements Page<E>, Serializable {

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

    public void setElements(List<E> elements) {
        this.elements = elements;
    }

    public boolean isFirstPage() {
        return getPageNumber()==FIRST_PAGE;
    }

    public boolean isLastPage() {
        return getPageNumber() >= getLastPageNumber();
    }

    public boolean hasNextPage() {
        return !isLastPage();
    }

    public boolean hasPreviousPage() {
        return getPageNumber() > FIRST_PAGE;
    }

    public int getLastPageNumber() {
        if(getTotalNumberOfElements()%getPageSize()==0){
            return getTotalNumberOfElements()/getPageSize();
        }else{
            return getTotalNumberOfElements()/getPageSize()+1;
        }
    }

    public List<E> getThisPageElements() {
        return elements;
    }


    public int getTotalNumberOfElements() {
        return totalNumberOfElements;
    }

    public int getThisPageFirstElementNumber() {
        return (getPageNumber()-1) * getPageSize() + 1;
    }

    public int getThisPageLastElementNumber() {
        int fullPage = getThisPageFirstElementNumber() + getPageSize() - 1;
        return getTotalNumberOfElements() < fullPage ?
                getTotalNumberOfElements() :
                fullPage;
    }

    public int getNextPageNumber() {
        return getPageNumber() + 1;
    }

    public int getPreviousPageNumber() {
        return getPageNumber() - 1;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }
}
