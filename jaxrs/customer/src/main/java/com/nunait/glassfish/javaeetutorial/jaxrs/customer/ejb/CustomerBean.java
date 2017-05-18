/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jaxrs.customer.ejb;

import com.nunait.glassfish.javaeetutorial.jaxrs.customer.data.Customer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 17 de mayo de 2017 23:45:14 ART
 */
@Named
@Stateless
public class CustomerBean {
    
    protected Client client;
    private static final Logger logger =
            Logger.getLogger(CustomerBean.class.getCanonicalName());
    
    @PostConstruct
    private void init() {
        client = ClientBuilder.newClient();
    }
    
    @PreDestroy
    private void clean() {
        client.close();
    }
    
    public String createCustomer(Customer customer) {
        if (customer == null) {
            logger.log(Level.WARNING, "customer es null.");
            return "customerError";
        }
        String navegation;
        Response response = 
                client.target("http://localhost:8080/customer/webapi/Customer")
                .request(MediaType.APPLICATION_XML)
                .post(Entity.entity(customer, MediaType.APPLICATION_XML),
                        Response.class);
        if (response.getStatus() == Status.CREATED.getStatusCode()) {
            navegation = "customerCreated";
        } else {
            logger.log(Level.WARNING,
                    "No se pudo crear el cliente con ID {0}. El estado retornado fue {1}",
                    new Object[]{customer.getId(), response.getStatus()});
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,
                    new FacesMessage("No se pudo crear cliente."));
            navegation = "customerError";
        }
        return navegation;
    }
    
    public String retrieveCustomer(String id) {
        String navegation;
        Customer customer =
                client.target("http://localhost:8080/customer/webapi/Customer")
                .path(id)
                .request(MediaType.APPLICATION_XML)
                .get(Customer.class);
        if (customer == null) {
            navegation = "customerError";
        } else {
            navegation = "customerRetrieved";
        }
        return navegation;
    }
    
    public List<Customer> retrieveAllCustomer() {
        List<Customer> customers =
                client.target("http://localhost:8080/customer/webapi/Customer")
                .path("all")
                .request(MediaType.APPLICATION_XML)
                .get(new GenericType<List<Customer>>(){
                });
        return customers;
    }
}
