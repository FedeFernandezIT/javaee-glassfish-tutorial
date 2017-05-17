/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jaxrs.rsvp.web;

import com.nunait.glassfish.javaeetutorial.jaxrs.rsvp.entity.Event;
import com.nunait.glassfish.javaeetutorial.jaxrs.rsvp.entity.Response;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 16 de mayo de 2017 21:18:12 ART
 */
@Named
@SessionScoped
public class EventManager implements Serializable {

    private static final long serialVersionUID = 7686835947120864267L;
    private static final Logger logger = Logger.getLogger(EventManager.class.getName());
    
    protected Event currentEvent;
    private Response currentResponse;
    private Client client;
    private final String baseUri = "http://localhost:8080/rsvp/webapi/status/";
    private WebTarget target;

    /**
     * Constructor por defecto que crea el Cliente JAX-RS
     */
    public EventManager() {
    }
    
    @PostConstruct
    private void init() {
        this.client = ClientBuilder.newClient();
    }
    
    @PreDestroy
    private void clean() {
        client.close();
    }

    public Event getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(Event currentEvent) {
        this.currentEvent = currentEvent;
    }

    public Response getCurrentResponse() {
        return currentResponse;
    }

    public void setCurrentResponse(Response currentResponse) {
        this.currentResponse = currentResponse;
    }

    /**
     * Recupera un colección de respuestas del evento actual.
     * 
     * @return una Lista de respuestas
     */
    public List<Response> retrieveEventResponses() {
        if (this.currentEvent == null) {
            logger.log(Level.WARNING, "evento actual es null");
        }
        logger.log(Level.INFO, "recuperando respuestas para {0}", this.currentEvent.getName());
        try {
            Event event = client.target(baseUri)
                    .path(this.currentEvent.getId().toString())
                    .request(MediaType.APPLICATION_XML)
                    .get(Event.class);
            if (event == null) {
                logger.log(Level.WARNING, "evento retornado es null");
                return null;
            } else {
                return event.getResponses();
            }                
        } catch (Exception e) {
            logger.log(Level.WARNING, "un error ha ocurrido al recuperar las respuestas del evento.");
            return null;
        }
    }
    
    /**
     * Establece el evento actual.
     * 
     * @param event el evento actual
     * @return una accíón JSF
     */
    public String retrieveEventStatus(Event event) {
        this.setCurrentEvent(event);
        return "eventStatus";
    }
    
    /**
     * Establece la respuesta actual y envía el caso de navegación.
     * 
     * @param response la respuesta que será vista
     * @return el case de navegación
     */
    public String viewResponse(Response response) {
        this.currentResponse = response;
        return "viewResponse";
    }
}
