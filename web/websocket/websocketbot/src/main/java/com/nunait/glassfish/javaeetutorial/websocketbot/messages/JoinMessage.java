/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.websocketbot.messages;

/** 
 * Representa un mensaje de unión (subscripción) al chat.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 11 de mayo de 2017 10:03:03 ART
 */
public class JoinMessage extends Message {
    
    private String name;

    public JoinMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    /* Para propósitos de logging */

    @Override
    public String toString() {
        return "[JoinMessage] " + name;
    }
    
}
