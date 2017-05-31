/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jms.vendor;

import java.util.Random;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Topic;

/** 
 * La clase Vendor usa una transacción para la recepción de pedidos de computadora
 * de un minorista y pide las cantidades necesarias de CPUs y discos duros a sus
 * proveedores. En intervalos aleatorios, esta lanza una excepción para simular
 * un problema de base de datos y causa un rollback.
 * 
 * La calse usa un listener de mensajes asincrónicos para procesar las replicas
 * de proveedores. Cuando todos las solicitudes a proveedores fueron atendidas,
 * esta envía un mensaje al minorista aceptando o rechazando el pedido.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 31 de mayo de 2017 11:30:15 ART
 */
public class Vendor {
    
    @Resource(lookup = "java:comp/DefaultJMSConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup = "jms/AQueue")
    private static Queue vendorOrderQueue;
    
    @Resource(lookup = "jms/CQueue")
    private static Queue vendorConfirmQueue;
    
    @Resource(lookup = "jms/OTopic")
    private static Topic supplierOrderTopic;
    
    static Random rgen = new Random();
    static int throwException = 1;
    
    public static void main(String[] args) {
        JMSConsumer vendorOrderReceiver;
        MapMessage orderMessage;
        
        JMSConsumer vendorConfirmReceiver;
        VendorMessageListener listener;
        Message inMessage;
        
        MapMessage vendorOrderMessage;
        Message endOfMessageStream;
        Order order;
        int quantity;
        
        System.out.println("Iniciando vendedor.");
        
        try (JMSContext context = 
                connectionFactory.createContext(JMSContext.SESSION_TRANSACTED);
             JMSContext asyncContext = 
                connectionFactory.createContext(JMSContext.SESSION_TRANSACTED);) {
            
            /*
             * Crea un receptor para vendor order queue, emisor para supplier
             * order topic, y el mensaje para enviar a los proveedores.
             */
            vendorOrderReceiver = context.createConsumer(vendorOrderQueue);
            orderMessage = context.createMapMessage();
            
            
            /*
             * Configura un listener de mensajes asincrónicos para replicas de
             * proveedores procesados para completar los pedidos. Incia el envío.
             */
            vendorConfirmReceiver = asyncContext.createConsumer(vendorConfirmQueue);
            listener = new VendorMessageListener(asyncContext, 2);
            vendorConfirmReceiver.setMessageListener(listener);
            
            
            while (true) {                
                try {
                    // Recibe un pedido del minorista.
                    inMessage = vendorOrderReceiver.receive();                    
                    
                    if (inMessage instanceof MapMessage) {
                        vendorOrderMessage = (MapMessage) inMessage;
                    } else {
                        /*
                     * Message es un end-of-message-stream del minorista. Envía
                     * un mensaje similar a los proveedores, para romper el
                     * bucle de procesamiento
                         */
                        endOfMessageStream = context.createMessage();
                        endOfMessageStream.setJMSReplyTo(vendorConfirmQueue);
                        context.createProducer().send(supplierOrderTopic, endOfMessageStream);
                        context.commit();
                        break;
                    }

                    /*
                 * Una aplicación real debería chequear el invenatrio de una base
                 * de datos. Aquí lanzamos una excepción cada vez que simulamos
                 * una excepción en el acceso a la base de datos y causamos un
                 * rollback.
                     */
                    if (rgen.nextInt(4) == throwException) {
                        throw new JMSException("Simulación de una excepción "
                                + "en el acceso concurrente a la base de datos");
                    }

                    /*
                 * Registra el pedido del minorista como pedido pendiente 
                     */
                    order = new Order(vendorOrderMessage);

                    /*
                 * Establecer número de pedido y reply queue para el mensaje saliente. 
                     */
                    orderMessage.setInt("VendorOrderNumber", order.orderNumber);
                    orderMessage.setJMSReplyTo(vendorConfirmQueue);
                    quantity = vendorOrderMessage.getInt("Quantity");
                    System.out.println("Vendedor: Minorista pidió " + quantity + " "
                            + vendorOrderMessage.getString("Item"));

                    /*
                 * Envía mensaje a supplier topic. Item no es utilizado por
                 * el proveedor.
                     */
                    orderMessage.setString("Item", "");
                    orderMessage.setInt("Quantity", quantity);
                    context.createProducer().send(supplierOrderTopic, orderMessage);
                    System.out.println("Vendedor: Pidió CPS(s) y Hard dirve(s).");

                    // Comitear sesión
                    context.commit();
                    System.out.println("Vendedor: Transacción #1 comiteada.");
                } catch (JMSException e) {
                    System.err.println("Vendedor: JMSException ha ocurrido. " + e.toString());
                    context.rollback();
                    System.err.println("Vendedor: Transacción #1 ha sido rollbackeada");
                }
            }
            
            
            // Espera hasta que todos los proveedores respondan.
            listener.monitor.waitTillDone();
        } catch (JMSRuntimeException e) {
            System.err.println("Vendedor: Una excepción ha ocurrido. " + e.toString());
            
        }
        
    }
}
