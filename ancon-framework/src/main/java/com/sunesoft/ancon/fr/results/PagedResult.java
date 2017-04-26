package com.sunesoft.ancon.fr.results;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Created by zhouz on 2016/5/11.
 */
public class PagedResult<T> implements Serializable {

    private final List<T> items;
    private final int pageSize;
    private final int pageNumber;
    private final int pagesCount;
    private final int totalItemsCount;
    private String oprator;

    public PagedResult(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        items = Collections.emptyList();
        pagesCount = 0;
        totalItemsCount = 0;
    }

    public PagedResult(List<T> items, int pageNumber, int pageSize, int totalItemsCount) {
        this.items = items;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.pagesCount = countPages(pageSize, totalItemsCount);
        this.totalItemsCount = totalItemsCount;
    }




    private int countPages(int size, int itemsCount) {
        return (int) Math.ceil((double) itemsCount / size);
    }

    public List<T> getItems() {
        return items;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public int getTotalItemsCount() {
        return totalItemsCount;
    }

    public String getOprator() {
        return oprator;
    }

    public void setOprator(String oprator) {
        this.oprator = oprator;
    }
}
