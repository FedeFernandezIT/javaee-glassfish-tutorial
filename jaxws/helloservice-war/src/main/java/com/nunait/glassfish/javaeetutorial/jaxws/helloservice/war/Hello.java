/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jaxws.helloservice.war;

import javax.jws.WebMethod;
import javax.jws.WebService;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 15 de mayo de 2017 17:58:21 ART
 */
@WebService
public class Hello {
    
    private String message = "Hola, ";

    public Hello() {
    }
    
    @WebMethod
    public String sayHello(String name) {
        return message + name + ".";
    }
}
