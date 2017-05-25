/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.persistence.order.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 23 de mayo de 2017 11:24:53 ART
 */
@Entity
@Table(name = "PERSISTENCE_ORDER_CUSTOMERORDER")
@NamedQuery(
        name = "findAllOrders",
        query = "SELECT co FROM CustomerOrder co ORDER BY co.orderId"
)
public class CustomerOrder implements Serializable {
    private static final long serialVersionUID = 2247173618999919061L;
    
    private Integer orderId;
    private char status;
    private Date lastUpdate;
    private int discount;
    private String shipmentInfo;
    private Collection<LineItem> lineItems;

    public CustomerOrder() {
        this.lastUpdate = new Date();
        this.lineItems = new ArrayList<>();
    }

    public CustomerOrder(Integer orderId, char status, 
            int discount, String shipmentInfo) {
        this.orderId = orderId;
        this.status = status;        
        this.discount = discount;
        this.shipmentInfo = shipmentInfo;
        this.lastUpdate = new Date();
        this.lineItems = new ArrayList<>();
    }
        
    @Id
    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getShipmentInfo() {
        return shipmentInfo;
    }

    public void setShipmentInfo(String shipmentInfo) {
        this.shipmentInfo = shipmentInfo;
    }

    @OneToMany(mappedBy = "customerOrder", cascade = CascadeType.ALL)
    public Collection<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(Collection<LineItem> lineItems) {
        this.lineItems = lineItems;
    }
    
    public double calculateAmmount() {
        double ammmount = 0;
        Collection<LineItem> items = getLineItems();
        for (LineItem item : items) {
            VendorPart part = item.getVendorPart();
            ammmount += part.getPrice() * item.getQuantity();
        }
        return ammmount * (100 - getDiscount())/100;
    }
    
    public void addLineItem(LineItem lineItem) {
        this.getLineItems().add(lineItem);
    }
    
    @Transient
    public int getNextId() {
        return this.lineItems.size() + 1;
    }
}
