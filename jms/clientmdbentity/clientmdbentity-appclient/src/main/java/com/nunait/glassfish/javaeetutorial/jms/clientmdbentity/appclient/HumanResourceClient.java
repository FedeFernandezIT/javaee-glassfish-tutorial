/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jms.clientmdbentity.appclient;

import java.util.Collections;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TemporaryQueue;
import javax.jms.Topic;

/** 
 * HumanResourceClient es un programa cliente para esta aplicación J2EE. Este
 * publica un mensaje describiendo un evento de negocio de un nuevo contratado
 * sobre el cual otros deparatamentos pueden actuar. Esta también escucha por
 * un mensaje de reporte de las acciones de otros departamentos y muestra los
 * resultados.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 1 de junio de 2017 16:46:02 ART
 */

@JMSDestinationDefinition(
        name = "java:app/jms/HRTopic",
        interfaceName = "javax.jms.Topic",
        destinationName = "PhysicalHRTopic")
public class HumanResourceClient {
    static final Logger logger = Logger.getLogger("HumanResourceClient");
    
    @Resource(lookup = "java:comp/DefaultJMSConnectionFactory")
    private static ConnectionFactory connectionFactory;
    @Resource(lookup = "java:app/jms/HRTopic")
    private static Topic pubTopic;
    
    static final Object waitUntilDone = new Object();
    static SortedSet<Object> outstadingRequest =
            Collections.synchronizedSortedSet(new TreeSet<>());
    
    
    /**
     * Métdo principal.
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        MapMessage message;
        TemporaryQueue replyQueue;
        JMSConsumer consumer;
        
        /**
         * Crea el contexto.
         * Crea una quque temporaria y un consumidor, establece un listener
         * de mensajes, e inicia el contexto.
         * Crea un MapMessage.
         * Crea un productor y publica un nuevo evento de negocio de nuevo contrato.
         * Espera hasta que todos los mennsajes sean procesados.
         * Finaliza y cierra el contexto.
         */
        try (JMSContext context = connectionFactory.createContext();) {
            Random rand = new Random();
            int nextHireID = rand.nextInt(1000);
            int[] order;
            
            String[] positions = {"Programador", "Programador Senior", "Gerente", "Director"};
            String[] names = {
                "Fred Verdon", "Robert Rogers", "Tom Stuart",
                "Mark Wilson", "James Anderson", "Wayne Reynolds",
                "Steve Waters", "Alfred Merman", "Joe Lawrence", "Jack Drake",
                "Harry Preston", "Bill Tudor", "Gertrude Windsor",
                "Jenny Hapsburg", "Polly Wren", "Ethel Parrott", "Mary Blair",
                "Betsy Bourbon", "Carol Jones", "Edna Martin", "Gwen Robbins",
                "Ann Thompson", "Cynthia Kelly", "Deborah Byrne"
            };
            
            replyQueue = context.createTemporaryQueue();
            consumer = context.createConsumer(replyQueue);
            consumer.setMessageListener(new HRListener());
            context.start();
            
            message = context.createMapMessage();
            message.setJMSReplyTo(replyQueue);
            order = getorder();
            
            for (int i = 0; i < 5; i++) {
                int currentHireID = nextHireID++;
                message.setString("HireID", String.valueOf(currentHireID));
                message.setString("Name", names[order[i]]);
                message.setString("Position", positions[rand.nextInt(positions.length)]);
                System.out.println("PUBLISHER: Estableciendo contrato " 
                        + message.getString("HireID") + " para "
                        + message.getString("Name") + ", posición "
                        + message.getString("Position"));
                context.createProducer().send(pubTopic, message);
                outstadingRequest.add(new Integer(currentHireID));
            }
            
            System.out.println("Esperando por " + outstadingRequest.size() + " mensaje(s).");
            synchronized (waitUntilDone) {
                waitUntilDone.wait();
            }
            
            
        } catch (JMSRuntimeException | JMSException | InterruptedException e) {
            logger.log(Level.SEVERE, "HumanResourceClient: Excepción: {0}",
                    e.toString());        
        }
        System.exit(0);
    }

    
    /**
     * Mas que el riego de nombres repetidos, genera un arrglo con las posiciones
     * de todos los posibles nombres en un orden aleatorio.
     * 
     * @return un arreglo ordenado de valores únicos aleatorios.
     */
    public static int[] getorder() {
        int[] order;
        Random rgen;
        
        order = new int[24];
        for (int i = 0; i < order.length; i++) {
            order[i] = i;
        }
        
        rgen = new Random();
        
        for (int i = 0; i < order.length; i++) {
            int randomPosition = rgen.nextInt(order.length);
            int tmp = order[i];
            order[i] = order[randomPosition];
            order[randomPosition] = tmp;
        }
        
        return order;
    }

    
    /**
     * HRListener implementa MessageListener definiendo el método onMessage.
     */
    static class HRListener implements MessageListener {

        /**
         * Muestra el contenido de un MapMessage que describe los resultados
         * del procesamiento de un nuevo empleado, y entonces remueve el ID de
         * empleado de la lista de solicitudes pendientes.
         * 
         * @param message el mensaje entrante
         */
        @Override
        public void onMessage(Message message) {
            MapMessage msg = (MapMessage) message;
            
            try {
                System.out.println("Evento Nuevo Contrato procesado:");
                
                Integer id = Integer.valueOf(msg.getString("employeeId"));
                System.out.println(" Empleado ID: " + id);
                System.out.println(" Nombre: " + msg.getString("employeeName"));
                System.out.println(" Equipamiento: " + msg.getString("equipmentList"));
                System.out.println(" Oficina #: " + msg.getString("officeNumber"));
                outstadingRequest.remove(id);
            } catch (JMSException e) {
                logger.log(Level.SEVERE, "HRListener.onMessage(): Excepción: {0}",
                        e.toString());
            }
            
            if (outstadingRequest.size() == 0) {
                synchronized (waitUntilDone) {
                    waitUntilDone.notify();
                }
            } else {
                System.out.println("Esperando por " + outstadingRequest.size() + " mensaje(s).");
            }                
        }
    }
    
}
