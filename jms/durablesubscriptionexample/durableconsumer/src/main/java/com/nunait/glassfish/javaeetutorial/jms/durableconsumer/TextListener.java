/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jms.durableconsumer;

import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 30 de mayo de 2017 11:34:59 ART
 */
public class TextListener implements MessageListener {

    @Override
    public void onMessage(Message m) {
        try {
            if (m instanceof TextMessage) {
                System.out.println("Recibiendo mensaje: " + m.getBody(String.class));
            } else {
                System.out.println("El mensaje no es es un TextMessage.");
            }
        } catch (JMSException | JMSRuntimeException e) {
            System.err.println("JMSException en onMessage(): " + e.toString());
        }         
    }

}
