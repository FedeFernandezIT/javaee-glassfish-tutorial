/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.cdi.producermethods;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/** 
 * Bean gestionado que invoca a una implementación de Coder
 * para realizar una transformación sobre una cadena de entrada.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 13 de mayo de 2017 16:13:13 ART
 */
@Named
@RequestScoped
public class CoderBean {
    
    private final static int TEST = 1;
    private final static int SHIFT = 2;
    private int coderType = SHIFT;  // Por defecto;
    
    private String inputString;
    private String codedString;
        
    @NotNull
    @Min(0) @Max(26)
    private int transVal;        
    
    @Inject @Chosen
    @RequestScoped
    Coder coder;
    
    /**
     * Método Productor que escoge entre dos beans basado en el
     * valor de coderType
     * 
     * @return implementación de coder escogida
     */
    @Produces @Chosen
    @RequestScoped
    public Coder getCoder() {
        switch (coderType) {
            case TEST:
                return new TestCoderImpl();
            case SHIFT:
                return new CoderImpl();
            default:
                return null;
        }
    }
    
    public void encodeString() {
        setCodedString(coder.codeString(inputString, transVal));
    }
    
    public void reset() {
        setInputString("");
        setTransVal(0);
    }

    public String getInputString() {
        return inputString;
    }

    public void setInputString(String inputString) {
        this.inputString = inputString;
    }

    public String getCodedString() {
        return codedString;
    }

    public void setCodedString(String codedString) {
        this.codedString = codedString;
    }

    public int getTransVal() {
        return transVal;
    }

    public void setTransVal(int transVal) {
        this.transVal = transVal;
    }

    public int getCoderType() {
        return coderType;
    }

    public void setCoderType(int coderType) {
        this.coderType = coderType;
    }        
        
}
