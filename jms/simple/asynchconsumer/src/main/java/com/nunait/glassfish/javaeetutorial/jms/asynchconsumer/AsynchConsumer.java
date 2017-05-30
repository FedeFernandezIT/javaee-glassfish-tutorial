/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jms.asynchconsumer;

import java.io.IOException;
import java.io.InputStreamReader;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;
import javax.jms.Queue;
import javax.jms.Topic;

/** 
 * La clase AsynchConsumer consiste sólo del método main, el cual recibe uno o
 * más mensajes de la queue o topic usando entrega asincrónica de mensajes. Esta
 * usa el listener TextListener. Ejecute este programa junto con Producer.
 * Especifique "queue" o "topic" en la línea de comandos cuando ejecute este
 * programa. Para finalizar la ejecución del mismo, ingreser 'Q' o 'q' en la
 * línea de comandos.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 29 de mayo de 2017 20:39:31 ART
 */
public class AsynchConsumer {
    
    @Resource(lookup = "java:comp/DefaultJMSConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup = "jms/MyQueue")
    private static Queue queue;
    
    @Resource(lookup = "jms/MyTopic")
    private static Topic topic;
    
    /**
     * Método main.
     * 
     * @param args nombre y tipo de destino usado por el ejemplo.
     */
    public static void main(String[] args) {
        String destType;
        Destination dest = null;
        JMSConsumer consumer;
        TextListener listener;
        InputStreamReader inputStreamReader;
        char answer = '\0';
        
        if (args.length != 1) {
            System.err.println("El programa debe un argumento: <tipo_destino>");
            System.exit(1);
        }
        
        destType = args[0];
        System.out.println("Tipo de destino: "  + destType);
        
        if (!(destType.equals("queue") || destType.equals("topic"))) {
            System.err.println("El argumento deben ser \"queue\" o \"topic\"");
            System.exit(1);
        }
                                
        try {
            if (destType.equals("queue")) {
                dest = (Destination) queue;                
            } else {
                dest = (Destination) topic;
            }
        } catch (JMSRuntimeException e) {
            System.err.println("Error al establecer destino: " + e.toString());
            System.exit(1);
        }
        
        /*
         * Dentro de un bloque try-with-resources, creamos un JMSContex.
         * Crea un consumidor.
         * Registra un listener (TextListener).
         * Recibe mensaje de texto desde el destino.
         * Cuando todos los mensajes son recibidos, presione Q para salir.
         */
        try (JMSContext context = connectionFactory.createContext()) {
            consumer = context.createConsumer(dest);
            listener = new TextListener();
            consumer.setMessageListener(listener);
            System.out.println("Para finalizar el programa, ingrese 'Q' o 'q', y presione <ENTER>");
            inputStreamReader = new InputStreamReader(System.in);
            
            while (!(answer == 'Q' || answer == 'q')) {
                try {
                    answer = (char) inputStreamReader.read();
                } catch (IOException e) {
                    System.err.println("Una excepción I/O ha ocurrido: " + e.toString());
                }
            }
        } catch (JMSRuntimeException e) {
            System.err.println("Una excepción ha ocurrido: " + e.toString());
            System.exit(1);
        }
        System.exit(0);
    }

}
