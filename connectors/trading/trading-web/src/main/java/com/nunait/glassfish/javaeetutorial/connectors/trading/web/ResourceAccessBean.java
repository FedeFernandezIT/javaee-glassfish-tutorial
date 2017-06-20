/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.connectors.trading.web;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.resource.ConnectionFactoryDefinition;
import com.nunait.glassfish.javaeetutorial.connectors.trading.rar.api.TradeConnection;
import com.nunait.glassfish.javaeetutorial.connectors.trading.rar.api.TradeConnectionFactory;
import com.nunait.glassfish.javaeetutorial.connectors.trading.rar.api.TradeOrder;
import com.nunait.glassfish.javaeetutorial.connectors.trading.rar.api.TradeProcessingException;

import com.nunait.glassfish.javaeetutorial.connectors.trading.rar.api.TradeResponse;
import javax.annotation.Resource;
import javax.resource.ResourceException;
import javax.resource.spi.TransactionSupport;

/** 
 * Managed bean for JSF pages that uses the RA Common Client Interface (CCI)
 * to submit trades to the EIS.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 11 de junio de 2017 16:04:35 ART
 */
@Named
@SessionScoped
@ConnectionFactoryDefinition(
        name = "java:comp/env/eis/TradeConnectionFactory",
        interfaceName = "com.nunait.glassfish.javaeetutorial.connectors.trading.rar.api.TradeConnectionFactory",
        resourceAdapter = "#trading-rar",
        minPoolSize = 5,
        transactionSupport = TransactionSupport.TransactionSupportLevel.NoTransaction
)
public class ResourceAccessBean implements Serializable {
    private static final long serialVersionUID = 5939050003311035548L;
    private static final Logger log = Logger.getLogger("TradeBean");
    
    @Resource(lookup = "java:comp/env/eis/TradeConnectionFactory")
    private TradeConnectionFactory connectionFactory;
    
    private TradeConnection  connection = null;
    private final TradeOrder order;
    private TradeResponse response;
    private String infoBox = "";

    public ResourceAccessBean() {
        this.order = new TradeOrder();
        order.setNShares(1000);
        order.setTicker(TradeOrder.Ticker.WWW);
        order.setOrderType(TradeOrder.OrderType.BUY);
        order.setOrderClass(TradeOrder.OrderClass.MARKET);
    }
    
    /* JSF navigation method (from index.xhtml) */
    public String connect() {
        String page = "index";
        if (connection == null) {
            try {
                log.info("[ResourceAccessBean] Obteniendo conexion desde el RA");
                connection = connectionFactory.getConnection();
                page = "trade";
            } catch (ResourceException e) {
                log.info(e.getMessage());
            }
        }
        return page;
    }
        
    /* JSF navigation method (from trade.xhtml) */
    public String disconnect() {
        infoBox = "";
        try {
            connection.close();
            connection = null;
        } catch (ResourceException e) {
            log.info(e.getMessage());
        }
        return "index";
    }
    
    /* JSF interface method to submit a trade to the RA/EIS (in trade.xhtml) */
    public void submitTrade() {
        infoBox = "\n -->" + order.toString() + infoBox;
        /* Use the Common Client Interface */
        try {            
            response = connection.submitOrder(order);
            infoBox = "\n <--" + response.toString() + infoBox;
        } catch (TradeProcessingException e) {
            infoBox = "\n <--ERROR " + e.getMessage() + infoBox;
        }
    }
    
    /* Getters and setters */
    public String getHost() {
        return "localhost";
    }
    public String getPort() {
        return "4004";
    }
    public TradeOrder getOrder() {
        return order;
    }   
    public String getInfoBox() {
        return infoBox;
    }
    public void setInfoBox(String infoBox) {
        this.infoBox = infoBox;
    }
    public TradeOrder.OrderClass[] getOrderClassList() {
        return TradeOrder.OrderClass.values();
    }
    public TradeOrder.OrderType[] getOrderTypeList() {
        return TradeOrder.OrderType.values();
    }
    public TradeOrder.Ticker[] getTickerList() {
        return TradeOrder.Ticker.values();
    }
}
