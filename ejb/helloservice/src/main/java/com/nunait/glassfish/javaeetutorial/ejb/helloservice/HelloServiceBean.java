/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.ejb.helloservice;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

/** 
 * HelloServiceBean es un Web Service Endpoint implementado
 * como un statless.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 19 de mayo de 2017 19:49:30 ART
 */
@Stateless
@WebService()
public class HelloServiceBean {
    private final String message = "Hola, ";

    public HelloServiceBean() {        
    }
    
    @WebMethod
    public String sayHello(String name) {
        return message + name + "!";
    }
}
