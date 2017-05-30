/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jms.asynchconsumer;

import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/** 
 * La clase TextListener implementa la interfaz MessageListener mediante la
 * definición del método onMessage que muestra el contenido de un TextMessage.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 29 de mayo de 2017 20:38:59 ART
 */
public class TextListener implements MessageListener {

    /**
     * Muestra el mesnsaje de texto.
     * 
     * @param message el mensaje entrante
     */
    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                System.out.println("Recibiendo mensaje: " + message.getBody(String.class));
            } else {
                System.out.println("El mensaje no es un TextMessage.");
            }
        } catch (JMSException | JMSRuntimeException e) {
            System.err.println("JMSException en onMessage(): " + e.toString());
        }
    }

}
