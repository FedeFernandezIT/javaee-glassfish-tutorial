/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jms.messagebrowser;

import java.util.Enumeration;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;

/** 
 * La clase MessageBrowser inspecciona una queue y muestra los mensajes que
 * esta posee.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 30 de mayo de 2017 9:10:37 ART
 */
public class MessageBrowser {
    
    @Resource(lookup = "java:comp/DefaultJMSConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup = "jms/MyQueue")
    private static Queue queue;
    
    
    /**
     * Método main.
     * 
     * @param args no son usados
     */
    public static void main(String[] args) {
        QueueBrowser browser;
        
        /*
         * Dentro de un bloque try-with-resources, creamos un JMSContex.
         * Crea un consumidor.
         * Recibe todos los mensajes de texto desde el destino hasta que un 
         * mensdaje sin texto es recibido indicando el final del flujo de
         * mensajes.
         */
        try (JMSContext context = connectionFactory.createContext()) {
            browser = context.createBrowser(queue);
            Enumeration msgs = browser.getEnumeration();
            
            if (!msgs.hasMoreElements()) {
                System.out.println("No hay mensajes en la queue.");
            } else {
                while (msgs.hasMoreElements()) {
                    Message tmpMsg = (Message) msgs.nextElement();
                    System.out.println("\nMensaje: " + tmpMsg);                    
                }
            }
        } catch (JMSException e) {
            System.err.println("Una excepción ha ocurrido: " + e.toString());
            System.exit(1);
        }
        System.exit(0);
    }

}
