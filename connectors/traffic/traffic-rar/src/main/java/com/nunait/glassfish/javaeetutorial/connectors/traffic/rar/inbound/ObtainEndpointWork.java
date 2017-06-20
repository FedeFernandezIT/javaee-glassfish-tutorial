/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.connectors.traffic.rar.inbound;

import com.nunait.glassfish.javaeetutorial.connectors.traffic.rar.TrafficResourceAdapter;
import java.util.logging.Logger;
import javax.resource.spi.UnavailableException;
import javax.resource.spi.endpoint.MessageEndpoint;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.resource.spi.work.Work;

/** 
 * This class is required only because obtaining an MDB endpoint
 * needs to be done in a different thread.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 11 de junio de 2017 23:04:29 ART
 */
public class ObtainEndpointWork implements Work {
    private static final Logger log = Logger.getLogger("ObtainEndpointWork");
    
    private TrafficResourceAdapter ra;
    private MessageEndpointFactory mef;
    private MessageEndpoint endpoint;

    public ObtainEndpointWork(TrafficResourceAdapter ra,
            MessageEndpointFactory mef) {
        this.ra = ra;
        this.mef = mef;        
    }

    public MessageEndpoint getMessageEndpoint() {
        return endpoint;
    }        

    @Override
    public void release() {                
    }

    @Override
    public void run() {        
        log.info("[ObtainEndpointWork] run()");
        try {
            /* Use the endpoint factory passed by the container upon
             * activation to obtain the MDB endpoint */
            endpoint = mef.createEndpoint(null);
            /* Return back to the resource adapter class */
            ra.endpointAvailable(endpoint);
        } catch (UnavailableException ex) {
            log.info(ex.getMessage());
        }
    }

}
