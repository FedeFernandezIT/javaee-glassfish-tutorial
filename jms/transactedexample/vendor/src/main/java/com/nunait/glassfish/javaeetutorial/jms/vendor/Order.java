/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jms.vendor;

import java.util.concurrent.ConcurrentHashMap;
import javax.jms.JMSException;
import javax.jms.MapMessage;

/** 
 * La clase Order representa el pedido de un minorista hecho a un vendedor. Esta
 * mantiene una tabla con pedidos pendientes.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 31 de mayo de 2017 9:14:00 ART
 */
public class Order {
    
    private static final int PENDING_STATUS = 1;
    private static final int CANCELLED_STATUS = 2;
    private static final int FULFILLED_STATUS = 3;
    
    private static final ConcurrentHashMap<Integer, Order> pendingOrders =
            new ConcurrentHashMap<>();
    private static int nextOrderNumber = 1;
    
    
    // Pedido orginal del minorista
    public final MapMessage order;
    public final int orderNumber;
    
    // Replica del proveedor
    public MapMessage cpu = null;
    public MapMessage storage = null;
    public int quantity;
    public int status;

    /**
     * Constructor. Establece el número de pedidos; establece pedido y cantidad
     * desde el mensaje entrante. Establece estado de pendientes, y agrega
     * a la tabla hash de pedidos pendientes.
     * 
     * @param order el mensaje que contiene el pedido
     */
    public Order(MapMessage order) {
        this.order = order;
        this.orderNumber = getNextOrderNumber();
        
        try {
            this.quantity = order.getInt("Quantity");
        } catch (JMSException e) {
            System.err.println("Pedido: Error no esperado. Mensaje perdió Cantidad.");
            this.quantity = 0;
        }
        
        status = PENDING_STATUS;
        pendingOrders.put(new Integer(orderNumber), this);
    }
    
    /**
     * Retorna el próximo número de pedido e incrementa la variable estática
     * que posee ese valor.
     * 
     * @return el próximo número de pedido
     */
    private static int getNextOrderNumber() {
        int result = nextOrderNumber;
        nextOrderNumber++;
        return result;
    }
            
    /**
     * Retorna el número de pedidos en la tabla hash.
     * 
     * @return el número de pedidos pendientes
     */
    public static int outstandingOrders() {
        return pendingOrders.size();
    }

    /**
     * Retorna el pedido correspondiente a un número de pedido.
     * 
     * @param orderNumber el número de pedido solicitado
     * @return el pedido solicitado
     */
    public static Order getOrder(int orderNumber) {
        return pendingOrders.get(new Integer(orderNumber));
    }
    
    public Order processSubOrder(MapMessage component) {
        String itemName = "";
        
        // Determina cual es el subcomponente de este.
        try {
            itemName = component.getString("Item");
        } catch (JMSException e) {
            System.err.println("Excepción no esperada. Mensaje perdió Item.");
        }
        
        if (itemName.compareTo("CPU") == 0) {
            cpu = component;
        } else if (itemName.compareTo("Hard Drive") == 0) {
            storage = component;
        }
        
        /*
         * Si todos los subcomponentes han recibido notificaciones, virifica las
         * cantidades para calcular si se puede completar el pedido.
         */
        if (cpu != null && storage != null) {
            try {
                if (quantity > cpu.getInt("Quantity")) {
                    status = CANCELLED_STATUS;
                } else if (quantity > storage.getInt("Quantity")) {
                    status = CANCELLED_STATUS;
                } else {
                    status = FULFILLED_STATUS;
                }
            } catch (JMSException e) {
                System.err.println("Excepción no esperada: " + e.toString());
                status = CANCELLED_STATUS;
            }
            
            /**
             * Precesamiento del pedido está completo, entonces remueve este
             * de los pedidos pendientes.
             */
            pendingOrders.remove(new Integer(orderNumber));
        }
        
        return this;
    }
    
    /**
     * Determina si el estado del pedido es pendiente.
     * 
     * @return true si el pedido está pendiente, false de lo contrario
     */
    public boolean isPending() {
        return status == PENDING_STATUS;
    }
    
    /**
     * Determina si el estado del pedido es cancelado.
     * 
     * @return true si el pedido está cancelado, false de lo contrario
     */
    public boolean isCancelled() {
        return status == CANCELLED_STATUS;
    }
    
    /**
     * Determina si el estado del pedido es completo.
     * 
     * @return true si el pedido está completo, false de lo contrario
     */
    public boolean isFulFilled() {
        return status == FULFILLED_STATUS;
    }
    
}
