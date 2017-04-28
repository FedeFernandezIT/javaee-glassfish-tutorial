/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.hello1.rlc;

import javax.enterprise.inject.Model;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 28 de abril de 2017 11:02:50 ART
 */
@Model
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
