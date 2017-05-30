/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jms.durableconsumer;

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
 * @created 30 de mayo de 2017 10:53:37 ART
 */
public class DurableConsumer {
    
    @Resource(lookup = "jms/DurableConnectionFactory")
    private static ConnectionFactory durableConnectionFactory;
    
    @Resource(lookup = "jms/MyTopic")
    private static Topic topic;
    
    
    public static void main(String[] args) {
        TextListener listener;
        JMSConsumer consumer;
        
        InputStreamReader inputStreamReader;
        char answer = '\0';
        
        /*
         * En un bloque try-with-resource creamos un context.
         * Crea un consumidor durable, si es que no existe aún.
         * Registra un listener de mensajes (TextListener).
         * Recibe mensajes del destino.
         * Cuando todos los mensajes hayan sido recibidos presione Q.
         */
        try (JMSContext context = durableConnectionFactory.createContext()) {
            System.out.println("Creando un consumer para el topic.");
            context.stop();
            consumer = context.createDurableConsumer(topic, "MakeItLast");
            listener = new TextListener();
            consumer.setMessageListener(listener);
            System.out.println("Iniciando consumer.");
            context.start();
            
            System.out.println("Para finalizar el programa ingrese 'Q' o 'q', y presione <ENTER>");
            inputStreamReader = new InputStreamReader(System.in);
            
            while (!(answer == 'Q' || answer == 'q')) {                
                try {
                    answer = (char) inputStreamReader.read();
                } catch (IOException e) {
                    System.err.println("Una excepcióon I/O ha ocurrido: " + e.toString());
                }
            }
        } catch (JMSRuntimeException e) {
            System.err.println("Una excepción ha ocurrido: " + e.toString());
            System.exit(1);
        }
        System.exit(0);
    }
}
