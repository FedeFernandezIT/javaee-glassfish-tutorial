/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jms.sharedconsumer;

import java.util.concurrent.atomic.AtomicLong;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/** 
 * TextListener implementa MessageListener definiendo el método onMessage que
 * muestra el contenido de un TextMessage.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 31 de mayo de 2017 16:38:44 ART
 */
public class TextListener implements MessageListener {
    
    AtomicLong count  = new AtomicLong(0);
    
    
    /**
     * Muestra el mensaje de texto.
     * 
     * @param message el mensaje entrante
     */
    @Override
    public void onMessage(Message message) {
        long i;
        
        try {
            if (message instanceof TextMessage) {
                i = count.incrementAndGet();
                // comentarla sigiuente línea para recibir varios mensajes
                System.out.println("Recibiendo mensaje: " + message.getBody(String.class));
            } else {
                System.err.println("El mensaje no es un TextMessage.");
            }
        } catch (JMSException e) {
            System.err.println("Excepción en onMessage(): " + e.toString());
        }
    }
    
    /*
     * Recupera el valor de count     
     */
    public long getCount() {
        return count.get();
    }
}
