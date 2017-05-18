/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jaxrs.customer.ejb;

import com.nunait.glassfish.javaeetutorial.jaxrs.customer.data.Customer;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 18 de mayo de 2017 0:17:44 ART
 */
@Model
public class CustomerManager implements Serializable {
    
    private static final long serialVersionUID = -3883224227781684253L;
    private static final Logger logger =
            Logger.getLogger(CustomerManager.class.getCanonicalName());
    
    @EJB
    private CustomerBean customerBean;
    
    private Customer customer;
    private List<Customer> customers;
    
    @PostConstruct
    private void init() {
        logger.info("nuevo cliente creado");
        customer = new Customer();
        setCustomers(customerBean.retrieveAllCustomer());
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Customer> getCustomers() {
        return customerBean.retrieveAllCustomer();
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }        
    
}
