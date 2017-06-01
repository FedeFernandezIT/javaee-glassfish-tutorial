/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jms.simplemessage.ejb;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 31 de mayo de 2017 22:57:25 ART
 */
@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/MyQueue"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class SimpleMessageBean implements MessageListener {
    private static final Logger logger = Logger.getLogger("SimpleMessageBean");
    
    @Resource
    private MessageDrivenContext mdc;
    
    @Override
    public void onMessage(Message inMessage) {
        try {
            if (inMessage instanceof TextMessage) {
                logger.log(Level.INFO, "MESSAGE BEAN: Mensaje recibido: {0}",
                        inMessage.getBody(String.class));
            } else {
                logger.log(Level.WARNING, "MESSAGE BEAN: Mensaje de tipo incorrecto: {0}",
                        inMessage.getClass().getName());
            }
        } catch (JMSException e) {
            logger.log(Level.SEVERE, "SimpleMessageBean.onMessage: JMSException {0}",
                        e.toString());
            mdc.setRollbackOnly();
        }
    }

}
