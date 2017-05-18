/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jaxrs.customer.resource;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 17 de mayo de 2017 21:09:43 ART
 */
@ApplicationPath("/webapi")
public class CustomerApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();
        // registra root resources
        classes.add(CustomerService.class);
        return classes;
    }
    
}
