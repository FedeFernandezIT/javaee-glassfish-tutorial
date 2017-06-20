/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.connectors.trading.rar;

import java.util.logging.Logger;
import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.Connector;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.transaction.xa.XAResource;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 11 de junio de 2017 12:31:36 ART
 */
@Connector(
        displayName = "TradeResourceAdapter",
        vendorName = "Nuna IT",
        version = "1.0.0"
)
public class TradeResourceAdapter implements ResourceAdapter {
    private static final Logger log = Logger.getLogger("TradeResourceAdapter");
    
    @Override
    public void start(BootstrapContext ctx) throws ResourceAdapterInternalException {
        log.info("[TradeResourceAdapter] start()");
    }

    @Override
    public void stop() {
        log.info("[TradeResourceAdapter] stop()");
    }
    
    /* These are called for inbound connectors */
    @Override
    public void endpointActivation(MessageEndpointFactory endpointFactory, ActivationSpec spec) throws ResourceException {
        log.info("[TradeResourceAdapter] endpointActivation()");
    }

    @Override
    public void endpointDeactivation(MessageEndpointFactory endpointFactory, ActivationSpec spec) {
        log.info("[TradeResourceAdapter] endpointDesactivation()");
    }

    /* This connector does not use transactions */
    @Override
    public XAResource[] getXAResources(ActivationSpec[] specs) throws ResourceException {
        return null;
    }

}
