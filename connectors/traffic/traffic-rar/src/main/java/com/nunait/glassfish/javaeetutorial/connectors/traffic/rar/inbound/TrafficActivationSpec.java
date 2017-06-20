/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.connectors.traffic.rar.inbound;

import com.nunait.glassfish.javaeetutorial.connectors.traffic.rar.api.TrafficCommand;
import com.nunait.glassfish.javaeetutorial.connectors.traffic.rar.api.TrafficListener;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.resource.ResourceException;
import javax.resource.spi.Activation;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.ConfigProperty;
import javax.resource.spi.InvalidPropertyException;
import javax.resource.spi.ResourceAdapter;

/** 
 * The activation spec used by the MDB to configure the resource adapter.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 11 de junio de 2017 22:38:47 ART
 */
@Activation(
        messageListeners = {TrafficListener.class}
)
public class TrafficActivationSpec implements ActivationSpec, Serializable {
    private static final Logger log = Logger.getLogger("TrafficActivationSpec");
    private static final long serialVersionUID = -4092524954857899424L;
        
    private ResourceAdapter ra;
    
    @ConfigProperty()
    private String port;
    
    private Class beanClass;
    private Map<String,Method> commands;

    public TrafficActivationSpec() throws InvalidPropertyException {
        commands = new HashMap<>();
    }
    
    /* Port is set by the MDB using @ActivationConfigProperty */
    public String getPort() {
        return port;
    }
    public void setPort(String port) {
        this.port = port;
    }
    
    /* Set from the RA class and accessed by the traffic subscriber thread */
    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }
    public Class getBeanClass() {
        return beanClass;
    }
    
    /* Inspect the MDB class for methods with a custom annotation.
     * This allows the MDB business interface to be emtpy */    
    public void findCommandInMDB() {
        log.info("[TrafficActivationSpec] findCommandsInMDB()");
        for (Method method : beanClass.getMethods()) {
            if (method.isAnnotationPresent(TrafficCommand.class)) {
                TrafficCommand tCommand = method.getAnnotation(TrafficCommand.class);
                commands.put(tCommand.name(), method);
            }
        }
        
        if (commands.isEmpty()) {
            log.info("No hay Asnotations para comandos dentro del MDB.");
        }
        
        for (Method m : commands.values()) {
            for (Class c : m.getParameterTypes()) {
                if (c != String.class) {
                    log.info("Los argumentos de comandos deben ser String.");
                }
            }
        }        
    }
    
    /* Used by the subscriber thread to invoke the discovered commands on the MDB */
    public Map<String, Method> getCommands() {
        return commands;
    }
    

    @Override
    public void validate() throws InvalidPropertyException {        
    }

    @Override
    public ResourceAdapter getResourceAdapter() {
        return ra;
    }

    @Override
    public void setResourceAdapter(ResourceAdapter ra) throws ResourceException {
        log.info("[TrafficActivationSpec] setResourceAdapter()");
        this.ra = ra;
    }

}
