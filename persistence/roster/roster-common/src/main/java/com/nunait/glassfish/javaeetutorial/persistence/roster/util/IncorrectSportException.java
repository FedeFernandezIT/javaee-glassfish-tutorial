/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.persistence.roster.util;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 25 de mayo de 2017 10:00:15 ART
 */
public class IncorrectSportException extends Exception {

    /**
     * Crea una nueva instancia de <code>IncorrectSportException</code>
     * sin detalles sobre el mensaje.
     */
    public IncorrectSportException() {
    }
    
    /**
     * Crea una nueva instancia de <code>IncorrectSportException</code>
     * con detalles sobre el mensaje especificado.
     * 
     * @param msg el mensaje detallado
     */
    public IncorrectSportException(String msg) {
        super(msg);
    }
}
