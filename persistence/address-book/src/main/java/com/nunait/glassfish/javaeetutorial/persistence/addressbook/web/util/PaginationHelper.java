/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.persistence.addressbook.web.util;

import javax.faces.model.DataModel;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 26 de mayo de 2017 12:44:01 ART
 */
public abstract class PaginationHelper {
    
    private int pageSize;
    private int page;

    public PaginationHelper(int pageSize) {
        this.pageSize = pageSize;
    }
    
    public abstract int getItemsCount();
    
    public abstract DataModel createPageDataModel();
    
    public int getPageFirstItem() {
        return page * pageSize;
    }
    
    public int getPageLastItem() {
        int i = getPageFirstItem() + pageSize - 1;
        int count = getItemsCount() - 1;
        if (i > count) {
            i = count;
        }
        if (i < 0) {
            i = 0;
        }
        return i;
    }
    
    public boolean isHasNextPage() {
        return (page + 1) * pageSize + 1 <= getItemsCount();
    }
    
    public void nexPage() {
        if (isHasNextPage()) {
            page++;
        }
    }
    
    public boolean isHasPreviousPage() {
        return page > 0;
    }
    
    public void previousPage() {
        if (isHasPreviousPage()) {
            page--;
        }
    }
    
    public int getPageSize() {
        return pageSize;
    }
}
