/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.connectors.trading.rar.outbound;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Set;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.resource.Referenceable;
import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;
import javax.resource.spi.ConfigProperty;
import javax.resource.spi.ConnectionDefinition;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterAssociation;
import javax.security.auth.Subject;

/** 
 * The container's connection manager uses this class to create a pool
 * of managed connections, which are associated at times with physical ones
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 11 de junio de 2017 13:27:51 ART
 */

/* Define classes an interfaces for the EIS physical connection */
@ConnectionDefinition(
        connectionFactory = ConnectionFactory.class,
        connectionFactoryImpl = TradeConnectionFactoryImpl.class,
        connection = Connection.class,
        connectionImpl = TradeConnectionImpl.class
)
public class TradeManagedConnectionFactory implements ManagedConnectionFactory,
        ResourceAdapterAssociation, Referenceable {
    private static final Logger log = Logger.getLogger("TradeManagedConnectionFactory");
    private static final long serialVersionUID = 7020797523645597196L;
    
    private ResourceAdapter ra;
    private Reference reference;
    private PrintWriter logWriter;
    private String host;
    private String port;
    
    public TradeManagedConnectionFactory() {
    }
    
    /* Configuration properties */
    public String getHost() {
        return host;
    }
    @ConfigProperty(defaultValue = "localhost")
    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }
    @ConfigProperty(type = String.class, defaultValue = "4004")
    public void setPort(String port) {
        this.port = port;
    }
    
    
    /* The application server provides a connection manager */
    @Override
    public Object createConnectionFactory(ConnectionManager cxManager) throws ResourceException {
        log.info("[TradeManagedConnectionFactory] createConnectionFactory()");
        return new TradeConnectionFactoryImpl(this, cxManager);
    }
    
    @Override
    public Object createConnectionFactory() throws ResourceException {        
        log.info("[TradeManagedConnectionFactory] createConnectionFactory()-NM");
        /* Non-managed connections not supported */
        return null;
    }

    /* Called by the application server to create a managed connection */
    @Override
    public ManagedConnection createManagedConnection(Subject subject, ConnectionRequestInfo cxRequestInfo) throws ResourceException {
        log.info("[TradeManagedConnectionFactory] createManagedConnection()");
        try {
            return new TradeManagedConnection(getHost(), getPort());
        } catch (IOException e) {
            throw new ResourceException(e.getCause());
        }        
    }

    /* When an application requests a connection, provide an existing one
     * from the pool if it matches the connection parameters */
    @Override
    public ManagedConnection matchManagedConnections(Set connectionSet, Subject subject, ConnectionRequestInfo cxRequestInfo) throws ResourceException {
        log.info("[TradeManagedConnectionFactory] matchManagedConnections()");        
        /* This resource adapter does not use security (Subject) */
        TradeManagedConnection match = null;
        /* This resource adapter has no additional parameters for connections,
         * so any open connection can be used by an application */
        for (Object mco : connectionSet) {
            if (mco != null) {
                match = (TradeManagedConnection) mco;
                log.info("Connection match!");
                break;
            }
        }
        return match;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws ResourceException {
        this.logWriter = out;
    }

    @Override
    public PrintWriter getLogWriter() throws ResourceException {
        return logWriter;
    }

    /* Called by the server to associate this object with the resource adapter */
    @Override
    public ResourceAdapter getResourceAdapter() {
        return ra;
    }

    @Override
    public void setResourceAdapter(ResourceAdapter ra) throws ResourceException {
        this.ra = ra;
    }

    /* Called by the server to associate an RA with a JNDI resource name.
     * Applications access an RA via JNDI resource injection (@Resource) */
    @Override
    public void setReference(Reference reference) {
        this.reference = reference;
    }

    @Override
    public Reference getReference() throws NamingException {
        return reference;
    }

}
