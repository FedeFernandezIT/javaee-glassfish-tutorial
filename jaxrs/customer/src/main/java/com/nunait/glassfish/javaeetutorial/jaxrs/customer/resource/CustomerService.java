/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jaxrs.customer.resource;

import com.nunait.glassfish.javaeetutorial.jaxrs.customer.data.Address;
import com.nunait.glassfish.javaeetutorial.jaxrs.customer.data.Customer;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/** 
 * Customer RESTful Service con métodos CRUD.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 17 de mayo de 2017 21:11:16 ART
 */
@Stateless
@Path("/Customer")
public class CustomerService {
    
    public static final Logger logger =
            Logger.getLogger(CustomerService.class.getCanonicalName());
    
    @PersistenceContext
    private EntityManager em;
    private CriteriaBuilder cb;
    
    @PostConstruct
    private void init() {
        cb = em.getCriteriaBuilder();
    }
    
    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Customer> getAllCustomes() {
        List<Customer> customers = null;
        try {
            customers = this.findAllCustomers();
            if (customers == null) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE,
                    "Error al invocar findAllCustomers(). {0}",
                    new Object[]{e.getMessage()});
        }
        return customers;
    }
    
    
    /**
     * Recupera un cliente en formato XML.
     * 
     * @param customerId
     * @return Customer
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Customer getCustomer(@PathParam("id") String customerId) {
        Customer customer = null;
        try {
            customer = findById(customerId);
        } catch (Exception e) {
            logger.log(Level.SEVERE,
                    "Error al invocar findCustomer() para customerId {0}. {1}",
                    new Object[]{customerId, e.getMessage()});
        }
        return customer;
    }
    
    
    /**
     * Método createCustomer basado sobre <code>Customer</code>.
     * 
     * @param customer
     * @return Response URI para el cliente agregado
     * @see Customer
     */
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createCustomer(Customer customer) {
        try {
            long customerId = persist(customer);
            return Response.created(URI.create("/" + customerId)).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, 
                    "Error al crear cliente con customerId {0}. {1}",
                    new Object[]{customer.getId(), e.getMessage()});
            throw new WebApplicationException(e,
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    /**
     * Actuliza un recurso.
     * 
     * @param customerId
     * @param customer
     * @return Response URI para el cliente actualizado
     * @see Customer
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response updateCustomer(@PathParam("id") String customerId,
            Customer customer) {
        try {
            Customer oldCustomer = findById(customerId);
            if (oldCustomer == null) {
                // return un not found en formato http/web
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            } else {
                persist(customer);
                return Response.ok().status(303).build();   // seeOther code
            }
        } catch (WebApplicationException e) {
            throw new WebApplicationException(e,
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    /**
     * Elimina un recurso.
     * 
     * @param customerId 
     */
    @DELETE
    @Path("{id}")
    public void deleteCustomer(@PathParam("id") String customerId) {
        try {
            if (!remove(customerId)) {
                throw new WebApplicationException(Response.Status.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE,
                    "Error al invocar deleteCustomer() para customerId {0}. {1}",
                    new Object[]{customerId, e.getMessage()});
        }
    }
    
    
    private List<Customer> findAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        try {
            customers = (List<Customer>) em.createNamedQuery("findAllCustomers").getResultList();
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error al buscar todos los clientes");
        }
        return customers;
    }

    
    /**
     * Consulta simple para encontrar un cliente por su ID.
     * 
     * @param customerId
     * @return Customer
     */
    private Customer findById(String customerId) {
        Customer customer = null;
        try {
            customer = em.find(Customer.class, customerId);
            return customer;
        } catch (Exception e) {
            logger.log(Level.WARNING,
                    "No se encontró cliente con ID {0}", customerId);
        }
        return customer;
    }

    
    /**
     * Método simple de persistencia
     * 
     * @param customer
     * @return customerId long
     */
    private long persist(Customer customer) {
        try {
            Address address = customer.getAddress();
            em.persist(address);
            em.persist(customer);
        } catch (Exception e) {
            logger.warning("Algo salió mal cuando se guardaba el cliente");
        }
        return customer.getId();
    }

    
    /**
     * Método remove simple para remover un cliente.
     * 
     * @param customerId
     * @return boolean
     */
    private boolean remove(String customerId) {
        Customer customer;
        try {
            customer = em.find(Customer.class, customerId);
            Address address = customer.getAddress();
            em.remove(address);
            em.remove(customer);
            return true;
        } catch (Exception e) {
            logger.log(Level.WARNING,
                    "No se pudo remover el cliente con ID {0}", customerId);
            return false;
        }
    }
}
