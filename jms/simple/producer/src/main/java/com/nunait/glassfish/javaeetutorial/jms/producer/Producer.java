/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jms.producer;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSRuntimeException;
import javax.jms.Queue;
import javax.jms.Topic;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 29 de mayo de 2017 17:04:19 ART
 */
public class Producer {

    @Resource(lookup = "java:comp/DefaultJMSConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup = "jms/MyQueue")
    private static Queue queue;
    
    @Resource(lookup = "jms/MyTopic")
    private static Topic topic;
    
    /**
     * Método main.
     * 
     * @param args el destino usado por los ejemplos, y opcionalmente
     * el número de mensajes para enviar.
     */
    public static void main(String[] args) {
        final int NUM_MSGS;
        
        if (args.length < 1 || args.length > 2) {
            System.err.println("El programa debe recibir dos argumentos: "
                + "<tipo_destino> [<cantidad_mensajes>]");
            System.exit(1);
        }
        
        String destType = args[0];
        System.out.println("Tipo de destino: "  + destType);
        
        if (!(destType.equals("queue") || destType.equals("topic"))) {
            System.err.println("Los argumentos deben ser \"queue\" o \"topic\"");
            System.exit(1);
        }
        
        if (args.length == 2) {
            NUM_MSGS = Integer.parseInt(args[1]);
        } else {
            NUM_MSGS = 1;
        }
        
        Destination dest = null;        
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
         * Crea un Producer y un Message.
         * Envía message a Destination.
         * Maneja errores.
         */
        try(JMSContext context = connectionFactory.createContext();) {
            int count = 0;
            
            for(int i = 0; i < NUM_MSGS; i++) {
                String message = "Este es el mensaje " + (i + 1)
                        + " enviado por el productor.";
                // comentar la siguiente línea para enviar varios mensajes
                System.out.println("Enviando mensaje: " + message);
                context.createProducer().send(dest, message);
                count += 1;
            }
            System.out.println("Mensajes de texto enviados: " + count);
            
            /*
             * Enviar un mensaje de control sin texto, indicando el fin
             * de los mensajes.
             */
            context.createProducer().send(dest, context.createMessage());
            // Descomentar la sigiuente línea si está enviando varios mensajes
            // hacia dos consumidores sincronizados.
            // context.createProducer().send(dest, context.createMessage());
        } catch (JMSRuntimeException e) {
            System.err.println("Una excepción ha ocurrido: " + e.toString());
            System.exit(1);
        }
        System.exit(0);
    }
}
