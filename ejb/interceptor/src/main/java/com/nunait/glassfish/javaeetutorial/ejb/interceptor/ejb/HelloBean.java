/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.ejb.interceptor.ejb;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.interceptor.Interceptors;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 20 de mayo de 2017 14:40:00 ART
 */
@Named
@Stateless
public class HelloBean {
    
    protected String name;

    public String getName() {
        return name;
    }

    @Interceptors(HelloInterceptor.class)
    public void setName(String name) {
        this.name = name;
    }
    
    
}
