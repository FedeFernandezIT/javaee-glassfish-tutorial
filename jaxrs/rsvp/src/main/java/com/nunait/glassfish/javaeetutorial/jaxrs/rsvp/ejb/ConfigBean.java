/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jaxrs.rsvp.ejb;

import com.nunait.glassfish.javaeetutorial.jaxrs.rsvp.entity.Event;
import com.nunait.glassfish.javaeetutorial.jaxrs.rsvp.entity.Person;
import com.nunait.glassfish.javaeetutorial.jaxrs.rsvp.entity.Response;
import com.nunait.glassfish.javaeetutorial.jaxrs.rsvp.util.ResponseEnum;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 16 de mayo de 2017 19:37:43 ART
 */

@Singleton
@Startup
public class ConfigBean {
    
    @PersistenceContext
    private EntityManager em;
    private static final Logger logger = Logger.getLogger(ConfigBean.class.getName());
    
    @PostConstruct
    public void init() {
        // crea el propietario del evento
        Person dad = new Person();
        dad.setFirstName("Diego");
        dad.setLastName("Maradona");
        em.persist(dad);
        
        // crea el evento
        Event event = new Event();
        event.setName("Fiesta de cumpleaños de Dalma");
        event.setLocation("Monumental");
        Calendar cal = new GregorianCalendar(2017, Calendar.JUNE, 12, 18, 0);
        event.setEventDate(cal.getTime());
        em.persist(event);
        
        // establece las relaciones
        dad.getOwnedEvents().add(event);
        dad.getEvents().add(event);
        event.setOwner(dad);
        event.getInvitees().add(dad);
        Response dadsResponse = new Response(event, dad, ResponseEnum.ATTENDING);
        em.persist(dadsResponse);
        event.getResponses().add(dadsResponse);
        
        // crea algunos invitados
        Person dalma = new Person();
        dalma.setFirstName("Dalma");
        dalma.setLastName("Maradona");
        em.persist(dalma);
        
        Person ariel = new Person();
        ariel.setFirstName("Ariel");
        ariel.setLastName("Ortega");
        em.persist(ariel);                
        
        // establece las relaciones
        event.getInvitees().add(dalma);
        dalma.getEvents().add(event);
        Response dalmasResponse = new Response(event, dalma);
        em.persist(dalmasResponse);
        event.getResponses().add(dalmasResponse);
        dalma.getResponses().add(dalmasResponse);
        
        event.getInvitees().add(ariel);
        ariel.getEvents().add(event);
        Response arielsResponse = new Response(event, ariel);
        em.persist(arielsResponse);
        event.getResponses().add(arielsResponse);
        ariel.getResponses().add(arielsResponse);
    }
}
