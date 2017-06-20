/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.connectors.traffic.war;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.MessageDriven;
import javax.inject.Named;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/** 
 * This bean asynchronously receives messages from a JMS
 * topic and calls a WebSocket server endpoint.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 20 de junio de 2017 10:44:32 ART
 */
@Named
@MessageDriven(mappedName = "java:app/traffic-ejb/traffictopic")
public class WebMDB implements MessageListener {
    private static final Logger log = Logger.getLogger("WebSocketMDB");
    
    @Override
    public void onMessage(Message message) {
        try {
            log.info("[WebMDB] onMessage()");
            String smsg = message.getBody(String.class);
            log.log(Level.INFO, "[WebMDB] Received: {0}", smsg);
            TrafficEndpoint.sendAll(smsg);
        } catch (JMSException e) {
            log.log(Level.INFO, "[WebMDB] Exception: {0}", e.getMessage());
        }
    }

}
