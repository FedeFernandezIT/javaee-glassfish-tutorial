/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.cdi.billpayment.listener;

import com.nunait.glassfish.javaeetutorial.cdi.billpayment.event.PaymentEvent;
import com.nunait.glassfish.javaeetutorial.cdi.billpayment.interceptor.Logged;
import com.nunait.glassfish.javaeetutorial.cdi.billpayment.payment.Credit;
import com.nunait.glassfish.javaeetutorial.cdi.billpayment.payment.Debit;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;

/** 
 * Handler para los dos tipos de PaymentEvent.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 14 de mayo de 2017 12:52:01 ART
 */
@Logged
@SessionScoped
public class PaymentHandler implements Serializable {        
    
    private static final long serialVersionUID = -2069377142577723120L;
    private static final Logger logger =
            Logger.getLogger(PaymentHandler.class.getCanonicalName());
            
    public PaymentHandler() {
        logger.log(Level.INFO, "PaymentHandler creado.");
    }
    
    public void creditPayment(@Observes @Credit PaymentEvent event) {
        logger.log(Level.INFO, "PaymentHandler - Credit Handler: {0}",
                event.toString());
        
        // llamar a un clase Handler específica para el evento Credit
    }
    
    public void debitPayment(@Observes @Debit PaymentEvent event) {
        logger.log(Level.INFO, "PaymentHandler - Debit Handler: {0}",
                event.toString());
        
        // llamar a un clase Handler específica para el evento Debit
    }
}
