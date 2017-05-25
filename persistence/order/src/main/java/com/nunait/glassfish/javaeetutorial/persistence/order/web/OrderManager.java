/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.persistence.order.web;

import com.nunait.glassfish.javaeetutorial.persistence.order.ejb.RequestBean;
import com.nunait.glassfish.javaeetutorial.persistence.order.entity.CustomerOrder;
import com.nunait.glassfish.javaeetutorial.persistence.order.entity.LineItem;
import com.nunait.glassfish.javaeetutorial.persistence.order.entity.Part;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 24 de mayo de 2017 9:20:49 ART
 */
@ManagedBean
@SessionScoped
public class OrderManager {
    
    @EJB
    private RequestBean request;
    private static final Logger logger =
            Logger.getLogger(OrderManager.class.getCanonicalName());
    
    private List<CustomerOrder> orders;
    private Integer currentOrder;
    private Integer newOrderId;
    private String newOrderShippingInfo;
    private char newOrderStatus;
    private int newOrderDiscount;
    private List<Part> newOrderParts;
    private List<Part> newOrderSelectedParts;
    
    private String vendorName;
    private List<String> vendorSearchResults;
    private String selectedPartNumber;
    private int selectedPartRevision;
    private Long selectedVendorPartNumber;
    
    private Boolean findVendorTableDisable = false;
    private Boolean partsTableDisable = false;

    public List<CustomerOrder> getOrders() {
        try {
            this.orders = request.getOrders();
        } catch (Exception e) {
            logger.warning("No se pudieron recuperar pedidos.");
        }
        return orders;
    }
    
    public List<LineItem> getLineItems() {
        try {
            return request.getLineItems(this.currentOrder);
        } catch (Exception e) {
            logger.warning("No se pudieron recuperar artículos de línea.");
            return null;
        }
    }
    
    public void removeOrder(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("deleteOrderId");
            Integer id = Integer.parseInt(param.getValue().toString());
            request.removeOrder(id);
            logger.log(Level.INFO, "Pedido {0} eliminado.", id);
        } catch (NumberFormatException nfe) {
        }
    }
    
    public void findVendor() {
        try {
            this.findVendorTableDisable = true;
            this.vendorSearchResults = request.locateVendorsByPartialName(vendorName);
            logger.log(Level.INFO,
                    "Vendedores encontrados {0} usando para la búsqueda el texto ''{1}''",
                    new Object[]{vendorSearchResults.size(), vendorName});
        } catch (Exception e) {
            logger.warning("Problema al invocar RequestBean.locateVendorsByPartialName desde findVendor");
        }
    }
    
    public void submitOrder() {
        try {
            request.createOrder(newOrderId, newOrderStatus, newOrderDiscount, newOrderShippingInfo);
            logger.log(Level.INFO, "Nuevo pedido creado con ID {0}, estado {1}, "
                    + "bonificación {2}, y datos de envío {3}",
                    new Object[]{newOrderId, newOrderStatus, newOrderDiscount, newOrderShippingInfo});
            
            this.newOrderId = null;
            this.newOrderDiscount = 0;
            this.newOrderParts = null;
            this.newOrderShippingInfo = null;
        } catch (Exception e) {
            logger.warning("Problema al crear un pedido en submitOrder");
        }
    }
    
    public void addLineItem() {
        try {
            List<LineItem> lineItems = request.getLineItems(currentOrder);
            logger.log(Level.INFO, "Hay {0} artículos de línea en {1}.",
                    new Object[]{lineItems.size(), currentOrder});
            request.addLineItem(this.currentOrder, this.selectedPartNumber,
                    this.selectedPartRevision, 1);
            logger.log(Level.INFO, "Agregando artículo de línea al pedido # {0}",
                    this.currentOrder);
            //this.clearSelected();
        } catch (Exception e) {
            logger.log(Level.WARNING, "Problema al agregar artículos de líena al pedido ID {0}",
                       newOrderId);
        }
    }

    public void setOrders(List<CustomerOrder> orders) {
        this.orders = orders;
    }

    public Integer getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Integer currentOrder) {
        this.currentOrder = currentOrder;
    }

    public Integer getNewOrderId() {
        return newOrderId;
    }

    public void setNewOrderId(Integer newOrderId) {
        this.newOrderId = newOrderId;
    }

    public String getNewOrderShippingInfo() {
        return newOrderShippingInfo;
    }

    public void setNewOrderShippingInfo(String newOrderShippingInfo) {
        this.newOrderShippingInfo = newOrderShippingInfo;
    }

    public char getNewOrderStatus() {
        return newOrderStatus;
    }

    public void setNewOrderStatus(char newOrderStatus) {
        this.newOrderStatus = newOrderStatus;
    }

    public int getNewOrderDiscount() {
        return newOrderDiscount;
    }

    public void setNewOrderDiscount(int newOrderDiscount) {
        this.newOrderDiscount = newOrderDiscount;
    }

    public List<Part> getNewOrderParts() {
        return request.getAllParts();
    }

    public void setNewOrderParts(List<Part> newOrderParts) {
        this.newOrderParts = newOrderParts;
    }

    public List<Part> getNewOrderSelectedParts() {
        return newOrderSelectedParts;
    }

    public void setNewOrderSelectedParts(List<Part> newOrderSelectedParts) {
        Iterator<Part> it = newOrderSelectedParts.iterator();
        while (it.hasNext()) {
            Part part = it.next();
            logger.log(Level.INFO, "Pieza {0} seleccionada.", part.getPartNumber());            
        }
        this.newOrderSelectedParts = newOrderSelectedParts;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public List<String> getVendorSearchResults() {
        return vendorSearchResults;
    }

    public void setVendorSearchResults(List<String> vendorSearchResults) {
        this.vendorSearchResults = vendorSearchResults;
    }

    public String getSelectedPartNumber() {
        return selectedPartNumber;
    }

    public void setSelectedPartNumber(String selectedPartNumber) {
        this.selectedPartNumber = selectedPartNumber;
    }

    public int getSelectedPartRevision() {
        return selectedPartRevision;
    }

    public void setSelectedPartRevision(int selectedPartRevision) {
        this.selectedPartRevision = selectedPartRevision;
    }

    public Long getSelectedVendorPartNumber() {
        return selectedVendorPartNumber;
    }

    public void setSelectedVendorPartNumber(Long selectedVendorPartNumber) {
        this.selectedVendorPartNumber = selectedVendorPartNumber;
    }

    private void clearSelected() {
        this.setSelectedPartNumber(null);
        this.setSelectedPartRevision(0);
        this.setSelectedVendorPartNumber(new Long(0));
    }
    
    public Boolean getFindVendorTableDisable() {
        return findVendorTableDisable;
    }

    public void setFindVendorTableDisable(Boolean findVendorTableDisable) {
        this.findVendorTableDisable = findVendorTableDisable;
    }

    public Boolean getPartsTableDisable() {
        return partsTableDisable;
    }

    public void setPartsTableDisable(Boolean partsTableDisable) {
        this.partsTableDisable = partsTableDisable;
    }    
    
}
