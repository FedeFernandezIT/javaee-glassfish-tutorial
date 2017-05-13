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
 * @created 12 de mayo de 2017 23:07:25 ART
 */
@Dependent
public class Greeting {
    public String greet(String name) {
        return "Bienvenido, " + name + ".";
    }
}
