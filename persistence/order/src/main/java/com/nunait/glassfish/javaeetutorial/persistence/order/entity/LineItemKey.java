/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.persistence.order.entity;

import java.io.Serializable;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 23 de mayo de 2017 12:01:34 ART
 */
public final class LineItemKey implements Serializable{
    private static final long serialVersionUID = -2845207656537437597L;
    
    private Integer customerOrder;
    private int itemId;

    public LineItemKey() {}

    public LineItemKey(Integer order, int itemId) {
        this.setCustomerOrder(order);
        this.setItemId(itemId);
    }

    @Override
    public int hashCode() {
        return this.getCustomerOrder() == null
                ? 0
                : this.getCustomerOrder().hashCode() ^this.getItemId();                    
    }    

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;        
        if (!(object instanceof LineItemKey)) return false;
        
        final LineItemKey other = (LineItemKey) object;
        
        return this.getCustomerOrder() == null
                ? other.getCustomerOrder() == null
                : this.getCustomerOrder().equals(other.getCustomerOrder())
                    && this.getItemId() == other.getItemId();
    }

    @Override
    public String toString() {
        return "" + getCustomerOrder() + "-" + getItemId();
    }
        
    public Integer getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(Integer customerOrder) {
        this.customerOrder = customerOrder;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }        
}
