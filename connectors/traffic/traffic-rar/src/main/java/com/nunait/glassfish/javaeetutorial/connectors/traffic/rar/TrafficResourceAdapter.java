/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.connectors.traffic.rar;

import com.nunait.glassfish.javaeetutorial.connectors.traffic.rar.inbound.ObtainEndpointWork;
import com.nunait.glassfish.javaeetutorial.connectors.traffic.rar.inbound.TrafficActivationSpec;
import com.nunait.glassfish.javaeetutorial.connectors.traffic.rar.inbound.TrafficServiceSubscriber;
import java.util.logging.Logger;
import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.Connector;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.resource.spi.endpoint.MessageEndpoint;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.resource.spi.work.Work;
import javax.resource.spi.work.WorkException;
import javax.resource.spi.work.WorkManager;
import javax.transaction.xa.XAResource;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 11 de junio de 2017 22:28:40 ART
 */

@Connector(
        displayName = "TrafficRA",
        vendorName = "Nuna IT",
        version = "1.0.0"
)
public class TrafficResourceAdapter implements ResourceAdapter {
    private static final Logger log = Logger.getLogger("TrafficResourceAdapter");
    
    private TrafficActivationSpec tSpec;
    private WorkManager workManager;
    private Work tSubscriber;
    
    /* Make the activation configuration available elswhere */
    public TrafficActivationSpec getActivationSpec() {
        return tSpec;
    }
    
    @Override
    public void start(BootstrapContext ctx) throws ResourceAdapterInternalException {
        log.info("[TrafficResourceAdapter] start()");
        /* Get the work manager from the container to submit tasks to
         * be executed in container-managed threads */
        workManager = ctx.getWorkManager();
    }

    @Override
    public void stop() {
        log.info("[TrafficResourceAdapter] stop()");
    }

    @Override
    public void endpointActivation(MessageEndpointFactory endpointFactory, ActivationSpec spec) throws ResourceException {
        log.info("[TrafficResourceAdapter] endpointActivation()"); 
        tSpec = (TrafficActivationSpec) spec;
        /* New in JCA 1.7 - Get the endpoint class implementation (i.e. the
         * MDB class). This allows looking at the MDB implementation for
         * annotations. */
        Class endpointClass = endpointFactory.getEndpointClass();
        tSpec.setBeanClass(endpointClass);
        tSpec.findCommandInMDB();
        
        /* MessageEndpoint msgEndpoint = endpointFactory.createEndpoint(null);
         * but we need to do that in a different thread, otherwise the MDB
         * never deploys. */
        ObtainEndpointWork work = new ObtainEndpointWork(this, endpointFactory);
        workManager.scheduleWork(work);
    }
    
    /* Called from ObtainEndpoint work after obtaining the endpoint */
    public void endpointAvailable(MessageEndpoint endpoint) {
        try {
            /* Start the traffic subscriber client in a new thread */
            tSubscriber = new TrafficServiceSubscriber(tSpec, endpoint);
            workManager.scheduleWork(tSubscriber);
        } catch (WorkException e) {
            log.info("[TrafficResourceAdapter] Can't start the subscriber");
            log.info(e.getMessage());
        }
    }

    @Override
    public void endpointDeactivation(MessageEndpointFactory endpointFactory, ActivationSpec spec) {
        log.info("[TrafficResourceAdapter] endpointDeactivation()");
        /* Stop listening */
        tSubscriber.release();
    }

    /* This connector does not use transactions */
    @Override
    public XAResource[] getXAResources(ActivationSpec[] specs) throws ResourceException {
        return null;
    }

}
