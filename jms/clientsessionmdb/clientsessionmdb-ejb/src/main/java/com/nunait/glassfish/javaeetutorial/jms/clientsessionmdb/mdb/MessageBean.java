    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jms.clientsessionmdb.mdb;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/** 
 * MessageBean es un message-driven-bean. Implementa javax.jms.MessageListener.
 * Esta definida como public (no final ni abstract).
 * 
 * Actualmente en un MDB debe definirse destination para ello se usa
 * destinationLookup; debe usar mappedName si destination esta definido.
 * (GlassFish issue 20715).
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 1 de junio de 2017 12:15:24 ART
 */
@JMSDestinationDefinition(
        name = "java:module/jms/newsTopic",
        interfaceName = "javax.jms.Topic",
        destinationName = "PhysicalNewsTopic")
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup",
                propertyValue = "java:module/jms/newsTopic"),
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "messageSelector",
                propertyValue = "NewsType = 'Sports' OR NewsType = 'Opinion'"),
        @ActivationConfigProperty(propertyName = "subscriptionDurability",
                propertyValue = "Durable"),
        @ActivationConfigProperty(propertyName = "clientId",
                propertyValue = "MyID"),
        @ActivationConfigProperty(propertyName = "subscriptionName",
                propertyValue = "MySub")
})
public class MessageBean implements MessageListener {
    public static final Logger logger = Logger.getLogger("MessageBean");
    
    @Resource
    public MessageDrivenContext mdc;

    public MessageBean() {
    }
        
    @Override
    public void onMessage(Message inMessage) {
        try {
            if (inMessage instanceof TextMessage) {
                logger.log(Level.INFO, "MESSAGE BEAN: Mensaje recibido: {0}",
                        inMessage.getBody(String.class));
            } else {
                logger.log(Level.WARNING, "Tipo de mensaje incorrecto: {0}",
                        inMessage.getClass().getName());
            }
        } catch (JMSException e) {
            logger.log(Level.SEVERE, "MessageBean.onMessage: JMSExcepcion {0}",
                    e.toString());
            mdc.setRollbackOnly();
        }
    }

}
