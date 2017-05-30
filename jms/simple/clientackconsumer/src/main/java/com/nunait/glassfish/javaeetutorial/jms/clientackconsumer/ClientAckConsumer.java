/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jms.clientackconsumer;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 30 de mayo de 2017 10:12:27 ART
 */
public class ClientAckConsumer {
    
    @Resource(lookup = "java:comp/DefaultJMSConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup = "jms/MyQueue")
    private static Queue queue;
    
    
    public static void main(String[] args) {
        JMSConsumer consumer;
        
        /*
         * Crea un context con acuse de recibo (acknowledge) para el receptor.
         * Recibe el mensaje y lo precesa.
         * Envía un acuse de recibo.
         */
        try (JMSContext context = connectionFactory.createContext(JMSContext.CLIENT_ACKNOWLEDGE)) {
            System.out.println("JMSContext client-acknowledge creado.");
            
            consumer = context.createConsumer(queue);
            
            while (true) {                
                Message m = consumer.receive(1000);
                
                if (m != null) {
                    if (m instanceof TextMessage) {
                        // Comentar las dos siguientes líneas al recibir
                        // grandes volúmenes de mensajes.
                        System.out.println(
                                "Recibiendo mensaje: " + m.getBody(String.class));
                        System.out.println("Acusando recibo de TextMessage.");
                        context.acknowledge();
                    } else {
                        System.out.println("Acusando recibo de mensaje de control.");
                        context.acknowledge();
                        break;
                    }
                }
            }
        } catch (JMSException e) {
            System.err.println("Una excepción ha ocurrido: " + e.toString());
        }
    }

}
