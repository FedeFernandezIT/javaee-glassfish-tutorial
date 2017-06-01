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
import javax.jms.JMSContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSRuntimeException;
import javax.jms.Queue;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 31 de mayo de 2017 20:29:24 ART
 */
@JMSDestinationDefinition(
        name = "java:comp/jms/webappQueue",
        interfaceName = "javax.jms.Queue",
        destinationName = "PhysicalWebappQueue")
@Named
@RequestScoped
public class SenderBean {
    static final Logger logger = Logger.getLogger("SenderBean");
    
    @Inject
    JMSContext context;    
    @Resource(lookup = "java:comp/jms/webappQueue")
    private Queue queue;
    
    private String messageText;

    public SenderBean() {
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
    
    public void sendMessage() {
        try {
            String text = "Mensaje del productor: " + messageText;
            context.createProducer().send(queue, text);
            
            FacesMessage facesMessage = new FacesMessage("Emite mensaje: " + text);
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        } catch (JMSRuntimeException e) {
            logger.log(Level.SEVERE, "SenderBean.sendMessage: Excepción {0}", e.toString());
        }
    }
}
