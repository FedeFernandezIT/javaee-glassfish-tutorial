/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jms.simplemessage.client;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 31 de mayo de 2017 23:24:34 ART
 */
public class SimpleMessageClient {
    static final Logger logger = Logger.getLogger("SimpleMessageClient");
    
    @Resource(lookup = "java:comp/DefaultJMSConnectionFactory")
    private static ConnectionFactory connectionFactory;
    @Resource(lookup = "jms/MyQueue")
    private static Queue queue;
    
    public static void main(String[] args) {
        final int NUM_MSG = 3;
        String text;
        
        try (JMSContext context = connectionFactory.createContext();) {
            
            for (int i = 0; i < NUM_MSG; i++) {
                text = "Este es el mensaje " + (i + 1);
                System.out.println("Emitiendo mensaje: " + text);
                context.createProducer().send(queue, text);
            }
            
            System.out.println("Ver si el bean ha recibido los mensajes,");
            System.out.println("chequear <install_dir>/domains/domain1/logs/server.log.");
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Una excepción ha ocurrido: {0}", e.toString());
            System.exit(1);
        }
        System.exit(0);
    }
}
