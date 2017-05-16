/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jaxrs.hello;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/** 
 * Root resource (expuesto en path "helloworld")
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 16 de mayo de 2017 12:37:04 ART
 */
@Path("helloworld")
public class HelloWorld {
    
    @Context
    private UriInfo context;

    /**
     * Crea una instancia de HelloWorld
     */
    public HelloWorld() {
    }
    
    /**
     * Recupera una representación de una instancia de HelloWorld
     * @return una instancia de java.lang.String
     */
    @GET
    @Produces("text/html")
    public String getHtml() {
        return "<html lang=\"es\"><body><h1>Hola, Mundo!</h1></body></html>";
    }
    
    /**
     * PUT method para crear o actualizar una instancia de HelloWorld
     * @param content representación para el resource
     */
    @PUT
    @Consumes("text/html")
    public void putHtml(String content) {        
    }
}
