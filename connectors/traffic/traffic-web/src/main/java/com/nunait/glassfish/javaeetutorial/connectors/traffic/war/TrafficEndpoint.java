/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.connectors.traffic.war;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/** 
 * This endpoint forwards to web clients the JSON messages 
 * received by the WebMDB bean from the JMS topic.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 20 de junio de 2017 10:44:21 ART
 */
@ServerEndpoint("/wstraffic")
public class TrafficEndpoint {
    private static final Logger log = Logger.getLogger("TrafficEndpoint");
    
    /* Queue for all open WebSocket sessions */
    static Queue<Session> queue = new ConcurrentLinkedQueue<>();
    
    /* Called by WebMDB when it receives messages from the JMS topic */
    public static synchronized void sendAll(String msg) {
        log.info("[TrafficEndpoint] sendAll()");
        
        try {
            /* Send messages from the JMS queue to all sessions */
            for (Session session : queue) {
                if (session.isOpen()) {
                    session.getBasicRemote().sendText(msg);
                    log.log(Level.INFO, "[TrafficEndpoint] Sent: {0}", msg);
                }
            }
        } catch (IOException e) {
            log.log(Level.INFO, "[TrafficEndpoint] Exception: {0}", e.getMessage());
        }
    }
    
    /* Add and remove sesions from the queue */
    @OnOpen
    public void openConnection(Session seesion) {
        log.info("[TrafficEndpoint] openConnection()");
        queue.add(seesion);
    }
    
    @OnClose
    public void closeConnection(Session session) {
        log.info("[TrafficEndpoint] closedConnection()");
        queue.remove(session);
    }
    
    public void error(Session session, Throwable t) {
        queue.remove(session);
        log.info("[TrafficEndpoint] error()");
    }
}
