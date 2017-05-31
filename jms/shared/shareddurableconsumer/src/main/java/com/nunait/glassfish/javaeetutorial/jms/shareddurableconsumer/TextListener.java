/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jms.shareddurableconsumer;

import java.util.concurrent.atomic.AtomicLong;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/** 
 * Muestra todos los mensajes de texto que se envían al topic.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 31 de mayo de 2017 18:38:42 ART
 */
public class TextListener implements MessageListener {
    
    AtomicLong count = new AtomicLong(0);
    
    @Override
    public void onMessage(Message message) {
        long i;
        
        try {
            if (message instanceof TextMessage) {
                i = count.incrementAndGet();
                // comentarla sigiuente línea para recibir varios mensajes
                System.out.println("Recibiendo mensaje: " + message.getBody(String.class));
            } else {
                System.out.println("El mensaje no es un TextMessage.");
            }
        } catch (JMSException e) {
            System.err.println("Excepción en onMessage(): " + e.toString());
        }        
    }
    
    public long getCount() {
        return count.get();
    }

}
