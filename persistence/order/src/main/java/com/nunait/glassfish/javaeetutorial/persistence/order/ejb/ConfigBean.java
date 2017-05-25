/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.persistence.order.ejb;

import java.util.Date;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 23 de mayo de 2017 20:18:57 ART
 */
@Singleton
@Startup
public class ConfigBean {

    @EJB
    private RequestBean request;
    
    @PostConstruct
    public void createData() {
        request.createPart("1234-5678-01", 1, "PIEZA ABC", new Date(),
                "PARTQWERTYUIOPASXDCFVGBHNJMKL", null);
        request.createPart("9876-4321-02", 2, "PIEZA DEF", new Date(),
                "PARTQWERTYUIOPASXDCFVGBHNJMKL", null);
        request.createPart("5456-6789-03", 3, "PIEZA GHI", new Date(),
                "PARTQWERTYUIOPASXDCFVGBHNJMKL", null);
        request.createPart("ABCD-XYZW-FF", 5, "PIEZA XYZ", new Date(),
                "PARTQWERTYUIOPASXDCFVGBHNJMKL", null);
        request.createPart("SDFG-ERTY-BN", 7, "PIEZA BOM", new Date(),
                "PARTQWERTYUIOPASXDCFVGBHNJMKL", null);
        
        request.addPartToBillOfMaterial("SDFG-ERTY-BN", 7,
                "1234-5678-01", 1);
        request.addPartToBillOfMaterial("SDFG-ERTY-BN", 7,
                "9876-4321-02", 2);
        request.addPartToBillOfMaterial("SDFG-ERTY-BN", 7,
                "5456-6789-03", 3);
        request.addPartToBillOfMaterial("SDFG-ERTY-BN", 7,
                "ABCD-XYZW-FF", 5);
        
        request.createVendor(100, "WidgetCorp",
                "Belgrano 100, San Salvador de Jujuy (4600), JUJUY",
                "Tapia Vicente", "888-777-9999");
        request.createVendor(200, "Gadget, Inc.",
                "24 de Septiembre 876, San Miguel de Tucumán (4100), TUCUMAN",
                "Díaz Rogelio", "866-345-6789");
        
        request.createVendorPart("1234-5678-01", 1,
                "PIEZA1", 100.00, 100);
        request.createVendorPart("9876-4321-02", 2,
                "PIEZA2", 10.44, 200);
        request.createVendorPart("5456-6789-03", 3,
                "PIEZA3", 76.23, 200);
        request.createVendorPart("ABCD-XYZW-FF", 5,
                "PIEZA4", 55.19, 100);
        request.createVendorPart("SDFG-ERTY-BN", 7,
                "PIEZA5", 345.87, 100);
        
        Integer orderId = new Integer(1110);
        request.createOrder(orderId, 'N', 10,
                "La Calandria 333, Unquillo (2988), CORDOBA");
        request.addLineItem(orderId, "1234-5678-01", 1, 3);
        request.addLineItem(orderId, "9876-4321-02", 2, 5);
        request.addLineItem(orderId, "ABCD-XYZW-FF", 5, 7);

        orderId = new Integer(4310);
        request.createOrder(orderId, 'N', 0,
                "La Calandria 333, Unquillo (2988), CORDOBA");
        request.addLineItem(orderId, "SDFG-ERTY-BN", 7, 1);
        request.addLineItem(orderId, "ABCD-XYZW-FF", 5, 3);
        request.addLineItem(orderId, "1234-5678-01", 1, 15);
    }
    
    @PreDestroy
    public void deleteData() {        
    }
}
