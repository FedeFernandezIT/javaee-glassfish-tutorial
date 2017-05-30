/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jms.unsubscriber;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSRuntimeException;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 30 de mayo de 2017 12:09:14 ART
 */
public class Unsubscriber {
    
    @Resource(lookup = "jms/DurableConnectionFactory")
    private static ConnectionFactory durableConnectionFactory;
    
    public static void main(String[] args) {
        try (JMSContext context = durableConnectionFactory.createContext()) {
            System.out.println("Desuscribiendo de suscripción duradera.");
            context.unsubscribe("MakeItLast");
        } catch (JMSRuntimeException e) {
            System.err.println("Una excepción ha ocurrido: " + e.toString());
            System.exit(1);
        }
        System.exit(0);
    }

}
