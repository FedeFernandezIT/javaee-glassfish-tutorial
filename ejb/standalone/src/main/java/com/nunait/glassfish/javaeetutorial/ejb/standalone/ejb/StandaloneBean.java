/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.ejb.standalone.ejb;

import javax.ejb.Stateless;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 20 de mayo de 2017 0:50:35 ART
 */
@Stateless
public class StandaloneBean {
    
    private static final String message = "Bienvenidos!";
    
    public String returnMessage() {
        return message;
    }
}
