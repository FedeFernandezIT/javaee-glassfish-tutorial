/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.connectors.trading.rar.outbound;

import com.nunait.glassfish.javaeetutorial.connectors.trading.rar.api.TradeConnection;
import com.nunait.glassfish.javaeetutorial.connectors.trading.rar.api.TradeConnectionFactory;
import java.util.logging.Logger;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;

/** 
 * Implements the class that applications use to request connection 
 * handles to the EIS 
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 11 de junio de 2017 13:23:18 ART
 */
public class TradeConnectionFactoryImpl implements TradeConnectionFactory {
    private static final Logger log = Logger.getLogger("TradeConnectionFactoryImpl");
    
    private ConnectionManager cmanager;
    private TradeManagedConnectionFactory mcfactory;
    
    /* The container creates instances of this class 
     * through TradeManagedConnectionFactory.createConnectionFactory() */    
    TradeConnectionFactoryImpl(TradeManagedConnectionFactory mcfactory,
            ConnectionManager cmanager) {
        this.mcfactory = mcfactory;
        this.cmanager = cmanager;        
    }

    /* Applications call this method, which delegates on the container's
     * connection manager to obtain a connection instance through
     * TradeManagedConnectionFactory */
    @Override
    public TradeConnection getConnection() throws ResourceException {
        log.info("[TradeConnectionFactoryImpl] getConnection()");
        return (TradeConnection) cmanager.allocateConnection(mcfactory, null);
    }

}
