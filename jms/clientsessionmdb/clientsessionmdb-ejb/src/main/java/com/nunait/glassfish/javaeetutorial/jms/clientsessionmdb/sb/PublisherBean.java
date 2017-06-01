/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jms.clientsessionmdb.sb;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.jms.Topic;

/** 
 * Bean para ejb Publisher. Implementa el método publishNews como así también
 * los métodos necesarios para la sesión stateless del bean.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 1 de junio de 2017 11:19:06 ART
 */
@Stateless
@Remote({
    PublisherRemote.class
})
public class PublisherBean implements PublisherRemote {
    private static final Logger logger = Logger.getLogger("PublisherBean");
    
    @Resource
    private SessionContext sc;
    @Resource(lookup = "java:module/jms/newsTopic")
    private Topic topic;
    @Inject
    private JMSContext context;
    
    static final String[] messageTypes = {
        "Nation/World", "Metro/Region", "Business", "Sports", "Living/Arts", "Opinion"
    };

    public PublisherBean() {
    }
        
    
    /**
     * Crea productores y mensajes. Envía mensajes después de establecer su
     * propiedad NewsType y usa el valor de la propiedad como mensaje de texto.
     * Los mensajes son recibidos por MessageBean, un message-driven-bean usa un
     * selector para recibir los mensajes cuya propiedad NewType tiene ciertos
     * valores.
     */
    @Override
    public void publisherNews() {
        TextMessage message;
        int numMsgs = messageTypes.length * 3;
        String messageType;
        
        try {
            message = context.createTextMessage();
            
            for (int i = 0; i < numMsgs; i++) {
                messageType = chooseType();
                message.setStringProperty("NewsType", messageType);
                message.setText("Artículo #" + i + ": " + messageType);
                logger.log(Level.INFO, "Estableciendo mensaje de texto para: {0}",
                        message.getText());
                context.createProducer().send(topic, message);
            }
        } catch (JMSException e) {
            logger.log(Level.SEVERE, "PublisherBean.publisherNews: Excepción: {0}", e.toString());
            sc.setRollbackOnly();
        }
    }

    /**
     * Selecciona en forma aleatoria el tipo de mensaje.
     * 
     * @return el string que representa el tipo de mansaje.
     */
    private String chooseType() {
        int wichMsg;
        Random rgen = new Random();        
        wichMsg = rgen.nextInt(messageTypes.length);
        return messageTypes[wichMsg];
    }

}
