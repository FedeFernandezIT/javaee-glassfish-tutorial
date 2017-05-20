/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.ejb.interceptor.ejb;

import java.util.logging.Logger;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 20 de mayo de 2017 14:41:33 ART
 */
public class HelloInterceptor {
    private final static Logger logger = Logger.getLogger("interceptor.ejb.HelloInterceptor");
    
    protected String gtreeting;

    public HelloInterceptor() {
    }
    
    @AroundInvoke
    public Object modifyGreeting(InvocationContext ctx) throws Exception {
        Object[] parameters = ctx.getParameters();
        String param = (String) parameters[0];
        param = param.toLowerCase();
        parameters[0] = param;
        ctx.setParameters(parameters);
        try {
            return ctx.proceed();
        } catch (Exception ex) {
            logger.warning("Error al invocar ctx.proceed en modifyGreeting()");
            return null;
        }        
    }    
}
