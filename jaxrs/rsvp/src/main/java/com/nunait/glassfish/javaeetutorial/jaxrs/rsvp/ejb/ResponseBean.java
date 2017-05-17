/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jaxrs.rsvp.ejb;

import com.nunait.glassfish.javaeetutorial.jaxrs.rsvp.entity.Response;
import com.nunait.glassfish.javaeetutorial.jaxrs.rsvp.util.ResponseEnum;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 16 de mayo de 2017 20:11:10 ART
 */
@Stateless
@Path("/{eventId}/{inviteId}")
public class ResponseBean {
    
    @PersistenceContext
    private EntityManager em;
    private static final Logger logger = Logger.getLogger(ResponseBean.class.getName());
        
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getResponse(@PathParam("eventId") Long eventId,
            @PathParam("inviteId") Long personId) {
        Response response = (Response)
                em.createNamedQuery("rsvp.entity.Response.findResponseByEventAndPerson")
                .setParameter("eventId", eventId)
                .setParameter("personId", personId)
                .getSingleResult();
        return response;
    }
    
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void putResponse(String userResponse,
            @PathParam("eventId") Long eventId,
            @PathParam("inviteId") Long personId) {
        logger.log(Level.INFO,
                "Actualizando estado a {0} para la persona con ID {1} para el evento con ID {2}",
                new Object[]{userResponse, personId, eventId});
        Response response = (Response)
                em.createNamedQuery("rsvp.entity.Response.findResponseByEventAndPerson")
                .setParameter("eventId", eventId)
                .setParameter("personId", personId)
                .getSingleResult();
        if (userResponse.equals(ResponseEnum.ATTENDING.getLabel()) 
                && !response.getResponse().equals(ResponseEnum.ATTENDING)) {
            response.setResponse(ResponseEnum.ATTENDING);
            em.merge(response);
            logger.log(Level.INFO, "Merge response");
        } else if (userResponse.equals(ResponseEnum.NOT_ATTENDING.getLabel()) 
                && !response.getResponse().equals(ResponseEnum.NOT_ATTENDING)) {
            response.setResponse(ResponseEnum.NOT_ATTENDING);
            em.merge(response);
            logger.log(Level.INFO, "Merge response");
        } else if (userResponse.equals(ResponseEnum.MAYBE_ATTENDING.getLabel()) 
                && !response.getResponse().equals(ResponseEnum.MAYBE_ATTENDING)) {
            response.setResponse(ResponseEnum.MAYBE_ATTENDING);
            em.merge(response);
            logger.log(Level.INFO, "Merge response");
        }
    }
}
