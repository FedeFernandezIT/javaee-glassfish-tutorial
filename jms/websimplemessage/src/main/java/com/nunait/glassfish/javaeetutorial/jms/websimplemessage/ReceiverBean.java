/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jms.websimplemessage;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSRuntimeException;
import javax.jms.Queue;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 31 de mayo de 2017 20:43:41 ART
 */
@Named
@RequestScoped
public class ReceiverBean {
    static final Logger logger = Logger.getLogger("ReceiverBean");
    
    @Inject
    JMSContext context;    
    @Resource(lookup = "java:comp/jms/webappQueue")
    private Queue queue;

    public ReceiverBean() {
    }
    
    public void getMessage() {
        try {
            JMSConsumer receiver = context.createConsumer(queue);
            String text = receiver.receiveBody(String.class, 1000);
            
            if (text != null) {
                FacesMessage facesMessage = new FacesMessage("Mensaje leído: "  + text);
                FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            } else {
                FacesMessage facesMessage = new FacesMessage("Ningún mensaje recibido después de 1 segundo");
                FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            }
                    
        } catch (JMSRuntimeException e) {
            logger.log(Level.SEVERE, "ReceiverBean.getMessage: Excepción {0}", e.toString());
        }
    }
    
}
