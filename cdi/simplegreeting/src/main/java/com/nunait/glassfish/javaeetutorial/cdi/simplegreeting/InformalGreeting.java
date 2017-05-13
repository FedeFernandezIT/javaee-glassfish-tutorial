/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.cdi.simplegreeting;

import javax.enterprise.context.Dependent;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 12 de mayo de 2017 23:19:49 ART
 */
@Informal
@Dependent
public class InformalGreeting extends Greeting {

    @Override
    public String greet(String name) {
        return "Hola, " + name + ".";
    }
    
}
