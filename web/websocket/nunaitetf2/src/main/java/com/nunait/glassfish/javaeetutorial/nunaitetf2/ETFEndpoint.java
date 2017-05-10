/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.nunaitetf2;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/** 
 * Versión WebSocket del ejemple nunaitetf.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 9 de mayo de 2017 19:45:07 ART
 */
@ServerEndpoint("/nunaitetf")
public class ETFEndpoint {
    
    private static final Logger logger = 
            Logger.getLogger(ETFEndpoint.class.getSimpleName());
    
    /* Cola para todas las sesiones abiertas de websocket */
    static Queue<Session> queue = new ConcurrentLinkedQueue<>();
    
    /* PriceVolumeBean invoca a este método para envias actualizaciones */
    public static void send(double price, int volume) {
        String msg = String.format("%.2f / %d", price, volume);
        try {
            /* Envía actualizaciones a todas las sesiones abiertas */
            for (Session session : queue) {
                session.getBasicRemote().sendText(msg);
                logger.log(Level.INFO, "Enviando: {0}", msg);
            }
        } catch (IOException e) {
            logger.log(Level.INFO, e.toString());
        }
    }
    
    @OnOpen
    public void openConnection(Session session) {
        /* Registra esta conexión en la cola */
        queue.add(session);
        logger.log(Level.INFO, "Conexión abierta.");
    }
    
    @OnClose
    public void closedConnection(Session session) {
        /* Remueve esta conexión de la cola*/
        queue.remove(session);
        logger.log(Level.INFO, "Conexión cerrada.");
    }
    
    @OnError
    public void error(Session session, Throwable t) {
        /* Remueve esta conexión de la cola*/
        queue.remove(session);
        logger.log(Level.INFO, t.toString());
        logger.log(Level.INFO, "Conexión fallida.");
    }
}
