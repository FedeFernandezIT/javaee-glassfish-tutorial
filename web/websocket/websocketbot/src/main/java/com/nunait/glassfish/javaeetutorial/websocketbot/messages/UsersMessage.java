/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.websocketbot.messages;

import java.util.List;

/** 
 * Representa una lista de usuarios actualmente conectados
 * al chat.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 11 de mayo de 2017 10:06:26 ART
 */
public class UsersMessage extends Message {

    private List<String> userlist;

    public UsersMessage(List<String> userlist) {
        this.userlist = userlist;
    }

    public List<String> getUserList() {
        return userlist;
    }
    
    /* Para propósitos de logging */
    @Override
    public String toString() {
        return "[UsersMessage] " + userlist.toString();
    }
    
}
