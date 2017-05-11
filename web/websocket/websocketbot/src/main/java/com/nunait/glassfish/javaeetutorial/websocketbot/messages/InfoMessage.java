/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.websocketbot.messages;

/** 
 * Representa un mensaje informativo, como el ingraso
 * al chat de un nuevo usuario o la salida de uno.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 11 de mayo de 2017 9:59:50 ART
 */
public class InfoMessage extends Message {
    
    private String info;

    public InfoMessage(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    
    /* Para propósitos de logging */
    @Override
    public String toString() {
        return "[InfoMessage] " + info;
    }
    
    
}
