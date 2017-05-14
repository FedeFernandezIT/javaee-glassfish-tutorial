/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.cdi.billpayment.interceptor;

import java.io.Serializable;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 14 de mayo de 2017 13:10:28 ART
 */
@Logged
@Interceptor
public class LoggedInterceptor implements Serializable {

    private static final long serialVersionUID = -588682108862967364L;

    public LoggedInterceptor() {
    }
    
    public Object logMethodEntry (InvocationContext invocationContext) 
            throws Exception {
        System.out.println("Método invocado: "
                + invocationContext.getMethod().getName() + " de la clase "
                + invocationContext.getMethod().getDeclaringClass().getName());
        return invocationContext.proceed();
    }

}
