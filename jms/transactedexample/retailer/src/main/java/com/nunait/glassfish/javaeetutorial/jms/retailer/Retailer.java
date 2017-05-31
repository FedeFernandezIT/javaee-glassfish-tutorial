/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jms.retailer;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;

/** 
 * La clase Retailer pide un número de computadoras enviando un mensaje al
 * vendedor. Entonces espera por que el pedido sea confirmado.
 *
 * En este ejemplo, el minosrista hace dos pedidos, uno especificando la
 * cantidad por línea de comandos, el otro el doble de esa cantidad.
 * 
 * Esta clase no usa transacciones.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 30 de mayo de 2017 19:31:12 ART
 */
public class Retailer {
    
    @Resource(lookup = "java:comp/DefaultJMSConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup = "jms/AQueue")
    private static Queue vendorOrderQueue;
    
    @Resource(lookup = "jms/BQueue")
    private static Queue retailerConfirmQueue;
    
    static int quantity = 0;
    
    public static void main(String[] args) {
        MapMessage outMessage;
        MapMessage inMessage;
        JMSConsumer orderConfirmReceiver;
        
        if (args.length != 1) {
            System.err.println("Error: El programa sólo acepta un argumento numérico.");
            System.exit(1);
        }
        
        quantity = Integer.parseInt(args[0]);
        System.out.println("Minorista: Cantidad para pedir es " + quantity);
        
        /*
         * Crea un contexto sin transacción y un emisor para vendor order queue.
         * Crea un mensaje para el vendedor, establece los valores de artículo
         * y cantidad.
         * Envía mensaje.
         * Crea un receptor para retailer confirm queue.
         * Recupera mensaje y reporta resultados.
         * Envía un mensaje end-of-message-stream para que el vendedor pare
         * el precesamiento de pedidos.
         */
        try (JMSContext context = connectionFactory.createContext();) {
            outMessage = context.createMapMessage();
            outMessage.setString("Item", "Computadora(s)");
            outMessage.setInt("Quantity", quantity);
            outMessage.setJMSReplyTo(retailerConfirmQueue);
            context.createProducer().send(vendorOrderQueue, outMessage);
            System.out.println("Minosrista: Pidió " + quantity + " computadora(s).");
            
            orderConfirmReceiver = context.createConsumer(retailerConfirmQueue);
            inMessage = (MapMessage) orderConfirmReceiver.receive();
            
            if (inMessage.getBoolean("OrderAccepted") == true) {
                System.out.println("Minorista: Pedido completo.");
            } else {
                System.out.println("Minorista: Pedido incompleto.");
            }
            
            System.out.println("Minosrista: Realizando otro pedido.");
            outMessage.setInt("Quantity", quantity * 2);
            context.createProducer().send(vendorOrderQueue, outMessage);
            System.out.println("Minosrista: Pidió " 
                    + outMessage.getInt("Quantity") + " computadora(s).");
            inMessage = (MapMessage) orderConfirmReceiver.receive();
            
            if (inMessage.getBoolean("OrderAccepted") == true) {
                System.out.println("Minorista: Pedido completo.");
            } else {
                System.out.println("Minorista: Pedido incompleto.");
            }
            
            /*
             * Envía un mensaje de control sin texto, indicando el
             * final de la emisión de mensajes.
             */
            context.createProducer().send(vendorOrderQueue, context.createMessage());
        } catch (JMSException e) {
            System.err.println("Minorista: Una excepción ha ocurrido " + e.toString());
        }
    }
}
