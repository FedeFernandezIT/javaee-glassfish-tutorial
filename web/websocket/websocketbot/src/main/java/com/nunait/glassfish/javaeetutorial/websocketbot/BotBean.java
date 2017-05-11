/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.websocketbot;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.inject.Named;

/** 
 * Agente Bot.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 11 de mayo de 2017 11:38:16 ART
 */
@Named
public class BotBean {
    
    /* Responde un mensaje desde el chat */
    public String respond(String msg) {
        String response;
        
        /* Remueve signos de interrogación */
        msg = msg.toLowerCase()
                .replaceAll("¿|\\?", "")
                .replaceAll("á", "a")
                .replaceAll("é", "e")
                .replaceAll("í", "i")
                .replaceAll("ó", "o")
                .replaceAll("ú", "u");
        
        if (msg.contains("como estas")) {
            response = "Estoy muy bien, gracias!";
        } else if (msg.contains("cuantos años tienes")) {
            Calendar nunaBirthday = new GregorianCalendar(1980, Calendar.APRIL, 3);
            Calendar now = GregorianCalendar.getInstance();
            int nunaAge = now.get(Calendar.YEAR) - nunaBirthday.get(Calendar.YEAR);
            response = String.format("Tengo %d años.", nunaAge);
        } else if (msg.contains("cuando es tu cumpleaños")) {
            response = "Mi cumpleaños es el 3 de Abril. Gracias por preguntar";
        } else if (msg.contains("tu color favorito")) {
            response = "el color que más me gusta es el azul. ¿El tuyo?";
        } else {
            response = "Perdón, no entiendo lo que me dices. ";
            response += "Puedes preguntarme como estoy, cuantos años tengo, o";
            response += "por mi color favorito.";
        }
        
        try {
            Thread.sleep(1200);
        } catch (InterruptedException ex) {}
        return response;
    }
}
