/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jms.genericsupplier;

import java.util.Random;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSRuntimeException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Topic;

/** 
 * La clase GenericSupplier recibe un pedido de artículos (order) desde un 
 * vendedor (vendor) y envía un mensaje aceptando o rechazando este.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 30 de mayo de 2017 17:08:47 ART
 */
public class GenericSupplier {
    
    @Resource(lookup = "java:comp/DefaultJMSConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup = "jms/OTopic")
    private static Topic supplierOrderTopic;
    
    static String PRODUCT_NAME;
    static boolean ready = false;
    static int quantity = 0;

    /**
     * Constructor. Instancia al proveedor (supplier) como el proveedor de un
     * tipo de artículo para ser pedido.
     * 
     * @param itemName el nombre del artículo que ha sido pedido
     */
    public GenericSupplier(String itemName) {
        PRODUCT_NAME = itemName;
    }
    
    /**
     * Método temporizador. Se completa cuando ready es establcido como true,
     * después que context es iniciado. Sleep previene al proveedor de sí mismo
     * de adelentarse en máquinas más rápidas.
     */
    void waitForTopicConsumer() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {            
        }
        
        while (!ready) {                        
        }
    }
    
    
    /**
     * Verifica si hay sufiencientes artículos en el inventario. En vez de ir a
     * una base de datos, este genera un número aleatorio y lo relaciona con la
     * cantidad pedida, así habrá veces que no haya suficiente stock.
     * 
     * @return la cantidad de artículos en el inventario.
     */
    public static int checkInventory() {
        Random rgen = new Random();
        return rgen.nextInt(quantity * 5);
    }
    
    public static void main(String[] args) {
        JMSConsumer receiver;
        Message inMessage;
        MapMessage orderMessage;
        MapMessage outMessage;
        
        if (args.length != 1) {
            System.out.println("El programa necesita un argumento, ya sea CPU o HD");
            System.exit(1);
        }
        PRODUCT_NAME = args[0];
        if ("HD".equals(PRODUCT_NAME)) {
            PRODUCT_NAME = "Hard Drive";
        }
        System.out.println("Iniciando proveedor " + PRODUCT_NAME);
        
        /*
         * Crea un contexto, luego crea un receptor para el topic de pedido, el
         * cual inicia el envío de mensaje.
         */
        try (JMSContext context = connectionFactory.createContext(JMSContext.SESSION_TRANSACTED);) {
            receiver = context.createConsumer(supplierOrderTopic);
            
            // contexto inicializado, establecer ready a true
            ready = true;
            
            /*
             * Mantener verificando supplier order topic para solicitud de
             * pedidos hasta que el mendsaje end-of-message-stream es recibido.
             * Recibe un pedido y envía una confirmación de pedido todo en una
             * sóla transacción
             */
            while (true) {                
                try {
                    inMessage = receiver.receive();
                    
                    if (inMessage instanceof MapMessage) {
                        orderMessage = (MapMessage) inMessage;
                    } else {
                        /*
                         * Message es un end-of-message-stream. Envía un
                         * mensaje similar a reply queue, commit la transacción,
                         * y después para el precesamiento de pedidos mediante
                         * la ruptura del bucle.
                         */
                        context.createProducer().send(inMessage.getJMSReplyTo(),
                                context.createMessage());
                        context.commit();
                        break;
                    }
                    
                    /*
                     * Extraer la cantidad pedida del mensaje de pedido.
                     */
                    quantity = orderMessage.getInt("Quantity");
                    System.out.println("Proveedor de " + PRODUCT_NAME + ": "
                        + "Vendedor encargó " + quantity + " " + PRODUCT_NAME + "(s)");
                    
                    /*
                     * Crea un emisor y un mensaje para reply queue.
                     * Establece un número de pedido y artículo; verifica
                     * el inventario y establece la cantidad disponible.
                     * Envía el mensaje al vendedor y commitea la transacción.
                     */
                    outMessage = context.createMapMessage();
                    outMessage.setInt("VendorOrderNumber", orderMessage.getInt("VendorOrderNumber"));
                    outMessage.setString("Item", PRODUCT_NAME);
                    
                    int numAvailable = checkInventory();
                    if (numAvailable >= quantity) {
                        outMessage.setInt("Quantity", quantity);
                    } else {
                        outMessage.setInt("Quantity", numAvailable);
                    }
                    
                    context.createProducer().send(
                            (Queue) orderMessage.getJMSReplyTo(), outMessage);
                    System.out.println("Proveedor de " + PRODUCT_NAME + ": "
                        + "Envía " + outMessage.getInt("Quantity") + " "
                        + outMessage.getString("Item") + "(s)");
                    
                    context.commit();
                    System.out.println("Proveedor de " + PRODUCT_NAME + ": "
                        + "Transacción comiteada.");
                } catch (Exception e) {
                    System.out.println("Proveedor de " + PRODUCT_NAME + ": "
                        + e.toString());
                }
            }
        } catch (JMSRuntimeException e) {
            System.out.println("Proveedor de " + PRODUCT_NAME + ": "
                        + e.toString());
        }
    }
}
