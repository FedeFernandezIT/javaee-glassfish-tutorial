/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jaxrs.rsvp.ejb;

import com.nunait.glassfish.javaeetutorial.jaxrs.rsvp.entity.Event;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 16 de mayo de 2017 20:41:16 ART
 */
@Named
@Stateless
@Path("/status")
public class StatusBean {
    
    @PersistenceContext
    private EntityManager em;
    private static final Logger logger = Logger.getLogger(StatusBean.class.getName());
    
    private List<Event> allCurrentEvents;
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{eventId}/")
    public Event getEvent(@PathParam("eventId") Long eventId) {
        Event event = em.find(Event.class, eventId);
        return event;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<Event> getAllCurrentEvents() {
        logger.info("Invocando getAllCurrentEvents");
        this.allCurrentEvents = (List<Event>)
                em.createNamedQuery("rsvp.entity.Event.getAllUpcomingEvent")
                .getResultList();
        if (this.allCurrentEvents == null) {
            logger.warning("No hay eventos actualmente!");
        }
        return this.allCurrentEvents;
    }
    
    
}
