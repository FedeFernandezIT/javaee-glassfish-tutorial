/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nunait.glassfish.javaeetutorial.jaxrs.rsvp.util;

/**
 *
 * @author Federico Fernandez
 */
public enum ResponseEnum {
    ATTENDING("Asistirá"),
    NOT_ATTENDING("No asistirá"),
    MAYBE_ATTENDING("Quizás"),
    NOT_RESPONDED("No respondió aún");
    
    private final String label;

    private ResponseEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }        
}
