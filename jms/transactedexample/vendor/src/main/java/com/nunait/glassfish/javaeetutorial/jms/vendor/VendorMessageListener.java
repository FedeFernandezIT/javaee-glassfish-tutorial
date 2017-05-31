/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jms.vendor;

import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;

/** 
 * La clase VendorMessageListener procesa un mensaje de confirmación de pedido
 * de un proveedor a un vendedor.
 * 
 * Esta demuestra el uso de transacciones dentro de listeners de mensajes.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 30 de mayo de 2017 20:25:38 ART
 */
public class VendorMessageListener implements MessageListener {
    final SampleUtilities.DoneLatch monitor = new SampleUtilities.DoneLatch();
    int numSuppliers;
    private final JMSContext context;

    /**
     * Constructor. Instancia el listener del mensaje con la sesión de la clase
     * consumidor (el vendedor).
     * 
     * @param context el contexto del consumidor
     * @param numSuppliers el número de proveedores
     */
    public VendorMessageListener(JMSContext context, int numSuppliers) {
        this.context = context;
        this.numSuppliers = numSuppliers;
    }

    
    /**
     * Castea el mensaje a un MapMessage y porcesa el pedido. Un mensaje que no
     * es un MapMessage es interpretado como el final del flujo de mensajes, y
     * y el listener del mensaje establece su estado de monitor para todos sus
     * mensajes procesados.
     * 
     * Cada mensaje recibido representa el cumplimiento de un mensaje por parte
     * del proveedor.
     * 
     * @param message el mensaje entrante
     */   
    @Override
    public void onMessage(Message message) {        
        /*
         * Si mmesage es el fin del flujo de mensaje y como es el último mensaje,
         * establece el estado del monitor para todos los perocesamiento de
         * mensajes hechos y comitea la transacción.
         */
        if (!(message instanceof MapMessage)) {
            if (Order.outstandingOrders() == 0) {
                numSuppliers--;
                if (numSuppliers == 0) {
                    monitor.allDone();
                }
            }
            
            try {
                context.commit();
            } catch (JMSRuntimeException e) {
            }
            return;
        }
        
        /*
         * Mensaje es un mensaje de conformación de pedido del proveedor.
         */
        try {
            MapMessage component = (MapMessage) message;
            
            /*
             * Procesar el mensaje de confirmación de pedido y comitear la transacción.            
             */
            int orderNumber = component.getInt("VendorOrderNumber");
            Order order = Order.getOrder(orderNumber).processSubOrder(component);
            context.commit();
            
            /*
             * Si este mensaje el el último del proveedor, enviar mensaje al
             * minorista y comitear transacción.
             */
            if (!order.isPending()) {
                System.out.println("Vendedor: Proceso completado para el pedido" + order.orderNumber);
                
                Queue replyQueue = (Queue) order.order.getJMSReplyTo();
                MapMessage retailerConfirmMessage = context.createMapMessage();
                
                if (order.isFulFilled()) {
                    retailerConfirmMessage.setBoolean("OrderAccepted", true);
                    System.out.println("Vendedor: Expide " + order.quantity + " computadora(s).");
                } else if (order.isCancelled()) {
                    retailerConfirmMessage.setBoolean("OrderAccepted", false);
                    System.out.println("Vendedor: No tiene para enviar " + order.quantity + " computadora(s).");
                }
                
                context.createProducer().send(replyQueue, retailerConfirmMessage);
                context.commit();
                System.out.println("Vendedor: Transacción #2 comiteada.");
            }            
        } catch (JMSException e) {
            System.out.println("JMSException: " + e.toString());
            try {
                context.rollback();
            } catch (JMSRuntimeException jre) {                
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e.toString());
            try {
                context.rollback();
            } catch (JMSRuntimeException jre) {
            }
        }
    }

}
