/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.cdi.simplegreeting;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 12 de mayo de 2017 23:28:56 ART
 */
@Named
@RequestScoped
public class Printer {
    
    @Inject
    //@Informal
    Greeting greeting;
    
    private String name;
    private String salutation;

    public void createSalutation() {
        this.salutation = greeting.greet(name);
    }
    
    public String getSalutation() {
        return salutation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
        
}
