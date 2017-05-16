/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jaxws.hello.appclient;

import com.nunait.glassfish.javaeetutorial.jaxws.helloservice.endpoint.Hello;
import com.nunait.glassfish.javaeetutorial.jaxws.helloservice.endpoint.HelloService;
import javax.xml.ws.WebServiceRef;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 15 de mayo de 2017 19:57:39 ART
 */
public class HelloAppClient {
    
    @WebServiceRef(wsdlLocation = 
            "http://localhost:8080/helloservice/HelloService?wsdl")
    private static HelloService service;
    
    /**     
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        System.out.println(sayHello("mundo")); 
    }

    private static String sayHello(String arg) {
        Hello port = service.getHelloPort();
        return port.sayHello(arg);
    }
}
