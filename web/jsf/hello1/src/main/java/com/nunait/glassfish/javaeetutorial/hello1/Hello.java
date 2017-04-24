/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.hello1;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/** 
 * Hello.class es un EJB
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 23 de abril de 2017 19:00:31 ART
 */

@Named
@RequestScoped
public class Hello {
    private String name;

    public Hello() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
        
}
