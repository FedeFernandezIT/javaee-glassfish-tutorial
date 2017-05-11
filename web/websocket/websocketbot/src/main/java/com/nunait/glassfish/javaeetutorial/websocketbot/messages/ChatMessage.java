/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.websocketbot.messages;

/** 
 * Representa un mensaje de chat.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 11 de mayo de 2017 9:54:14 ART
 */
public class ChatMessage extends Message {
    private String name;
    private String target;
    private String message;

    public ChatMessage(String name, String target, String message) {
        this.name = name;
        this.target = target;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getTarget() {
        return target;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    /* Para propósitos de logging. */

    @Override
    public String toString() {
        return "[ChatMessage] " + name + "-" + target + "-" + message;
    }
    
}


