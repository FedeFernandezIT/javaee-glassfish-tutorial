/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jms.shareddurableconsumer;

import java.io.IOException;
import java.io.InputStreamReader;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSRuntimeException;
import javax.jms.Topic;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 31 de mayo de 2017 18:44:03 ART
 */
public class SharedDurableConsumer {
    
    @Resource(lookup = "java:comp/DefaultJMSConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup = "jms/MyTopic")
    private static Topic topic;
    
    public static void main(String[] args) {
        JMSConsumer consumer;
        TextListener listener;
        InputStreamReader inputStreamReader;
        char answer = '\0';
                
        try (JMSContext context = connectionFactory.createContext();) {
            consumer = context.createSharedDurableConsumer(topic, "MakeItLast");
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
