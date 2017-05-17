/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jaxrs.rsvp.web;

import com.nunait.glassfish.javaeetutorial.jaxrs.rsvp.entity.Event;
import com.nunait.glassfish.javaeetutorial.jaxrs.rsvp.entity.Person;
import com.nunait.glassfish.javaeetutorial.jaxrs.rsvp.util.ResponseEnum;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.ResponseProcessingException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 17 de mayo de 2017 9:51:02 ART
 */
@Named
@SessionScoped
public class StatusManager implements Serializable {

    private static final long serialVersionUID = -757070640072082142L;
    private static final Logger logger = Logger.getLogger(StatusManager.class.getName());
    
    private Event event;
    private List<Event> events;
    
    private Client client;
    private final String baseUri = "http://localhost:8080/rsvp/webapi";
    private WebTarget target;

    /**
     * Constructor por defecto que crea el cliente JAX-RS
     */
    public StatusManager() {
        this.client = ClientBuilder.newClient();
    }
    
    @PreDestroy
    public void clean() {
        client.close();
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    /**
     * Recupera todos los eventos.
     * 
     * @return todos los eventos
     */
    public List<Event> getEvents() {
        List<Event> returnedEvents = null;
        try {
            returnedEvents = client.target(baseUri)
                    .path("/status/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<Event>>(){
                    });
            if (returnedEvents == null) {
                logger.log(Level.SEVERE, "Eventos retornados es null.");            
            } else {
                logger.log(Level.INFO, "Eventos han sido retornados.");
            }
        } catch (WebApplicationException e) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        } catch (ResponseProcessingException e) {
            logger.log(Level.SEVERE, "ResponseProcessingException lanzada.");
            logger.log(Level.SEVERE, "Error es {0}", e.getMessage());
        } catch (ProcessingException e) {
            logger.log(Level.SEVERE, "ProcessingException lanzada.");
            logger.log(Level.SEVERE, "Error es {0}", e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Errors al recuperar todos los eventos.");
            logger.log(Level.SEVERE, "URI base es {0}", baseUri);
            logger.log(Level.SEVERE, "path es {0}", "all");
            logger.log(Level.SEVERE, "Exception es {0}", e.getMessage());
        }
        return returnedEvents;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
    
    
    /**
     * Establece el evento.
     * 
     * @param event el evento actual     
     * @return una acción JSF
     */
    public String getEventStatus(Event event) {
        this.setEvent(event);
        return "eventStatus";
    }
    
    
    /**
     * Recupera los valores de estados.
     * 
     * @return un array de valores de respuestas.
     */
    public ResponseEnum[] getStatusValues() {
        return ResponseEnum.values();
    }
    
    
    public String changeStatus(ResponseEnum userResponse, Person person, Event event) {
        String navegation;
        try {
            logger.log(Level.INFO, 
                    "cambiando estado a {0} para {1} {2} en el evento {3}",
                    new Object[]{person.getFirstName(),
                        person.getLastName(),
                        event.getId().toString()});
            client.target(baseUri)
                    .path(event.getId().toString())
                    .path(person.getId().toString())
                    .request(MediaType.APPLICATION_XML)
                    .post(Entity.xml(userResponse.getLabel()));
            navegation = "changedStatus";
        } catch (ResponseProcessingException e) {
            logger.log(Level.WARNING,
                    "Pdodría ser que no se haya cambiado el estado para {0} {1}",
                    new Object[]{person.getFirstName(),
                        person.getLastName()});
            logger.log(Level.WARNING, e.getMessage());
            navegation = "error";
        }
        return navegation;
    }
}
