/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.connectors.trading.rar.outbound;

import com.nunait.glassfish.javaeetutorial.connectors.trading.rar.api.TradeConnection;
import com.nunait.glassfish.javaeetutorial.connectors.trading.rar.api.TradeOrder;
import com.nunait.glassfish.javaeetutorial.connectors.trading.rar.api.TradeProcessingException;
import com.nunait.glassfish.javaeetutorial.connectors.trading.rar.api.TradeResponse;
import java.io.IOException;
import java.util.logging.Logger;
import javax.resource.ResourceException;

/** 
 * An application-level connection handle used by clients to access
 * the physical connection. The physical connection is represented by
 * a ManagedConnection instance.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 11 de junio de 2017 13:38:20 ART
 */
public class TradeConnectionImpl implements TradeConnection {
    private static final Logger log = Logger.getLogger("TradeConnectionImpl");
    
    private TradeManagedConnection mconnection;
    private boolean valid;

    TradeConnectionImpl(TradeManagedConnection mconnection) {
        this.mconnection = mconnection;
        this.valid = true;
    }

    /* Called by the managed connection to dis/associate this handle. */
    TradeManagedConnection getManagedConnection() {
        return mconnection;
    }    
    void setManagedConnection(TradeManagedConnection mconnection) {    
        this.mconnection = mconnection;
    }
    
    /* Called by the managed connection to invalidate this handle */
    void invalidate() {
        this.valid = false;
    }

    /* Submits a trade order to the EIS */
    @Override
    public TradeResponse submitOrder(TradeOrder order) throws TradeProcessingException {
        log.info("[TradeConnectionImpl] submitOrder()");
        if (valid) {
            try {
                String response = mconnection.sendCommandToEIS(order.toString());                
                return new TradeResponse(response);
            } catch (IOException e) {
                throw new TradeProcessingException(e.getMessage());
            }
        } else
            throw new TradeProcessingException("Manejo de conexión inválido.");        
    }

    /* Closes the connection handle */
    @Override
    public void close() throws ResourceException {
        log.info("[TradeConnectionImpl] close()");
        valid = false;
        mconnection.disassociateConnection();
    }

}
