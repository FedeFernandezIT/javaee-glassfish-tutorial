/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.persistence.order.ejb;

import com.nunait.glassfish.javaeetutorial.persistence.order.entity.CustomerOrder;
import com.nunait.glassfish.javaeetutorial.persistence.order.entity.LineItem;
import com.nunait.glassfish.javaeetutorial.persistence.order.entity.Part;
import com.nunait.glassfish.javaeetutorial.persistence.order.entity.PartKey;
import com.nunait.glassfish.javaeetutorial.persistence.order.entity.Vendor;
import com.nunait.glassfish.javaeetutorial.persistence.order.entity.VendorPart;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 23 de mayo de 2017 17:37:49 ART
 */
@Stateful
public class RequestBean {
    
    @PersistenceContext
    private EntityManager em;
    
    private static final Logger logger = 
            Logger.getLogger(RequestBean.class.getCanonicalName());
    
    // <editor-fold defaultstate="collapsed" desc="Part managament">
    public void createPart(String partNumber, int revision, String description,
            Date revisionDate, String specification, Serializable drawing) {        
        try {
            Part part = new Part(partNumber, revision, description,
                    revisionDate, specification, drawing);
            logger.log(Level.INFO, "Pieza {0}-{1} creada.", new Object[]{partNumber, revision});
            em.persist(part);
            logger.log(Level.INFO, "Pieza {0}-{1} registrada.", new Object[]{partNumber, revision});
        } catch (Exception e) {
            throw  new EJBException(e.getMessage());
        }
    }
    
    public List<Part> getAllParts() {
        List<Part> parts = (List<Part>) em.createNamedQuery("findAllParts").getResultList();
        return parts;
    }
    
    public void addPartToBillOfMaterial(String bomPartNumber, int bomRevision,
            String partNumber, int revision) {
        logger.log(Level.INFO, "BOM Pieza número: {0}", bomPartNumber);
        logger.log(Level.INFO, "BOM Pieza lote: {0}", bomRevision);
        logger.log(Level.INFO, "Pieza número: {0}", partNumber);
        logger.log(Level.INFO, "Pieza lote: {0}", revision);
        try {
            PartKey bomKey = new PartKey();
            bomKey.setPartNumber(bomPartNumber);
            bomKey.setRevision(bomRevision);
            
            Part bom = em.find(Part.class, bomKey);
            logger.log(Level.INFO, "BOM Pieza encontrada: {0}", bom.getPartNumber());
            
            PartKey partKey = new PartKey();
            partKey.setPartNumber(partNumber);
            partKey.setRevision(revision);
            
            Part part = em.find(Part.class, partKey);
            logger.log(Level.INFO, "Pieza encontrada: {0}", part.getPartNumber());
            bom.getParts().add(part);
            part.setBomPart(bom);
        } catch (EJBException e) {
        }        
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Vendor managament">
    public void createVendor(int vendorId, String name, String address,
            String contact, String phone) {
        try {
            Vendor vendor = new Vendor(vendorId, name, address, contact, phone);
            em.persist(vendor);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void createVendorPart(String partNumber, int revision, String description,
            double price, int vendorId) {
        try {
            PartKey pkey = new PartKey();
            pkey.setPartNumber(partNumber);
            pkey.setRevision(revision);
            
            Part part = em.find(Part.class, pkey);
            
            VendorPart vendorPart = new VendorPart(description, price, part);
            em.persist(vendorPart);
            
            Vendor vendor = em.find(Vendor.class, vendorId);
            vendor.addVendorPart(vendorPart);
            vendorPart.setVendor(vendor);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Order managament">
    public void createOrder(Integer orderId, char status, int discount, String shipmentInfo) {
        try {
            CustomerOrder order = new CustomerOrder(orderId, status, discount, shipmentInfo);
            em.persist(order);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<CustomerOrder> getOrders() {
        try {
            return (List<CustomerOrder>) em.createNamedQuery("findAllOrders").getResultList();
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void addLineItem(Integer orderId, String partNumber, int revision, int quantity) {
        try {
            CustomerOrder order = em.find(CustomerOrder.class, orderId);
            logger.log(Level.INFO, "Pedido ID {0} encontrado.", orderId);
            
            PartKey pkey = new PartKey();
            pkey.setPartNumber(partNumber);
            pkey.setRevision(revision);
            
            Part part = em.find(Part.class, pkey);
            
            LineItem lineItem = new LineItem(order, quantity, part.getVendorPart());
            order.getLineItems().add(lineItem);
        } catch (Exception e) {
            logger.log(Level.WARNING, "No se pudo agregar {0} al pedido ID {1}.",
                    new Object[]{partNumber, orderId});
            throw new EJBException(e.getMessage());
        }
    }
    
    public double getBillOfMaterialPrice(String bomPartNumber, int bomRevision) {
        double price = 0.0;
        try {
            PartKey bomkey = new PartKey();
            bomkey.setPartNumber(bomPartNumber);
            bomkey.setRevision(bomRevision);
            
            Part bom = em.find(Part.class, bomkey);
            Collection<Part> parts = bom.getParts();
            for (Part part : parts) {
                VendorPart vendorPart = part.getVendorPart();
                price += vendorPart.getPrice();
            }
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
        return price;
    }
    
    public double getOrderPrice(Integer orderId) {
        double price = 0.0;
        try {
            CustomerOrder order = em.find(CustomerOrder.class, orderId);
            price = order.calculateAmmount();
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
        return price;
    }
    
    public void adjustOrderDiscount(int adjustment) {
        try {
            List orders = em.createNamedQuery("findAllOrders").getResultList();
            for (Iterator it = orders.iterator(); it.hasNext();) {
                CustomerOrder order = (CustomerOrder) it.next();
                int newDiscount = order.getDiscount();
                order.setDiscount(newDiscount > 0 ? newDiscount : 0);
            }
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public Double getAvgPrice() {
        try {
            return (Double) em.createNamedQuery("findAverageVendorPartPrice")
                    .getSingleResult();
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public Double getTotalPricePerVendor(int vendorId) {
        try {
            return (Double) em.createNamedQuery("findTotalVendorPartPricePerVendor")
                    .setParameter("id", vendorId)
                    .getSingleResult();
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<String> locateVendorsByPartialName(String name) {
        List<String> names = new ArrayList<>();
        try {
            List vendors = em.createNamedQuery("findVendorsByPartialName")
                    .setParameter("name", name)
                    .getResultList();
            for (Iterator iterator = vendors.iterator(); iterator.hasNext();) {
                Vendor vendor = (Vendor)iterator.next();
                names.add(vendor.getName());                
            }
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
        return names;
    }
    
    public int countAllItems() {
        int count = 0;
        try {
            count = em.createNamedQuery("findAllLineItems")
                    .getResultList()
                    .size();
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
        return count;
    }
    
    public List<LineItem> getLineItems(int orderId) {
        try {
            return em.createNamedQuery("findLineItemByOrderId")
                    .setParameter("orderId", orderId)
                    .getResultList();
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public void removeOrder(Integer orderId) {
        try {
            CustomerOrder order = em.find(CustomerOrder.class, orderId);
            em.remove(order);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public String reportVendorsByOrder(Integer orderId) {
        StringBuilder report = new StringBuilder();
        try {
            List vendors = em.createNamedQuery("findVendorByCustomerOrder")
                    .setParameter("id", orderId)
                    .getResultList();
            for (Iterator iterator = vendors.iterator(); iterator.hasNext();) {
                Vendor vendor = (Vendor)iterator.next();
                report.append(vendor.getVendorId()).append(' ')
                        .append(vendor.getName()).append(' ')
                        .append(vendor.getContact()).append('\n');                
            }
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
        return report.toString();
    }
    // </editor-fold>
}
