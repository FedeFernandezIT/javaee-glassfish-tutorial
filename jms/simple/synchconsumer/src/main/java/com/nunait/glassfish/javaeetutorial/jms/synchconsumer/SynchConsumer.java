package com.nunait.glassfish.javaeetutorial.jms.synchconsumer;
        
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.jms.Topic;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/** 
 * La clase SynchConsumer consiste sólo de un método main, el cual recupera uno
 * o más mensajes desde una queue o un topic usando entrega sincronizada de 
 * mensajes.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 29 de mayo de 2017 19:01:16 ART
 */
public class SynchConsumer {
    
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
         * Crea un navagdor para queue (QueueBrowser).
         * Chequeamos los mensajes que contiene.         
         */
        try (JMSContext context = connectionFactory.createContext();) {
            consumer = context.createConsumer(dest);
            int count = 0;
            
            while (true) {                
                Message m = consumer.receive(1000);
                
                if (m != null) {
                    if (m instanceof TextMessage) {
                        // Comentar las dos siguientes líneas al recibir
                        // grandes volúmenes de mensajes.
                        System.out.println(
                                "Recibiendo mensaje: " + m.getBody(String.class));
                        count += 1;
                    } else {
                        break;
                    }
                }
            }
            System.out.println("Mensajes recibidos: " + count);
        } catch (JMSException e) {
            System.err.println("Una excepción ha ocurrido: " + e.toString());
            System.exit(1);
        }
        System.exit(0);
    }

}
