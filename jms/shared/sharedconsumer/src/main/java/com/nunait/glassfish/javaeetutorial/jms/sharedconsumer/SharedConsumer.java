/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jms.sharedconsumer;

import java.io.IOException;
import java.io.InputStreamReader;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSRuntimeException;
import javax.jms.Topic;

/** 
 * SharedConsumer consiste en sólo del método main, el cual recupera uno o más
 * mensajes desde un topic usando envío asincrónico de mensajes. Usa un listener
 * TextListener.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 31 de mayo de 2017 13:33:46 ART
 */
public class SharedConsumer {
    
    @Resource(lookup = "java:comp/DefaultJMSConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup = "jms/MyTopic")
    private static Topic topic;
    
    public static void main(String[] args) {
        JMSConsumer consumer;
        TextListener listener;
        InputStreamReader inputStreamReader;
        char answer = '\0';
        
        /**
         * Crea un contexto en un bloque try-with-resources.
         * Crea un consumidor compartido.
         * Recibe mensajes hatsta que un mensaje de control
         * indica el final del flujo de mensajes.
         */
        try (JMSContext context = connectionFactory.createContext();) {
            consumer = context.createSharedConsumer(topic, "SubName");
            System.out.println("Esperando por mensajes del topic.");
            
            listener = new TextListener();
            consumer.setMessageListener(listener);
            System.out.println("Para finalizar el programa, ingrese 'Q' o 'q' y presiones <ENTER>.");
            inputStreamReader = new InputStreamReader(System.in);
            
            while (!(answer == 'Q' || answer == 'q')) {                
                try {
                    answer = (char) inputStreamReader.read();
                } catch (IOException e) {
                    System.err.println("Excepción I/O: " + e.toString());
                }
            }
            System.out.println("Mensajes de texto recibidos: " + listener.getCount());
        } catch (JMSRuntimeException e) {
            System.err.println("Una excepción ha ocurrido: " + e.toString());
            System.exit(1);
        }
        System.exit(0);
    }

}
