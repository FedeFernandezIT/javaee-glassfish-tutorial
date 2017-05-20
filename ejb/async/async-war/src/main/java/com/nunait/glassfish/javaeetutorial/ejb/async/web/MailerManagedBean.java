/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.ejb.async.web;

import com.nunait.glassfish.javaeetutorial.ejb.async.ejb.MailerBean;
import java.io.Serializable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 20 de mayo de 2017 13:02:16 ART
 */
@Named
@SessionScoped
public class MailerManagedBean implements Serializable {
    
    private static final long serialVersionUID = 9157367523550608458L;
    private static final Logger logger = 
            Logger.getLogger(MailerManagedBean.class.getName());
    
    @EJB
    protected MailerBean mailerBean;
    protected String email;
    protected String status;
    private Future<String> mailStatus;

    public MailerManagedBean() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        if (mailStatus.isDone()) {
            try {
                this.setStatus(mailStatus.get());
            } catch (InterruptedException | CancellationException |
                     ExecutionException ex) {
                this.setStatus(ex.getCause().toString());
            }
        }
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String send() {
        String response = "response?faces-redirect=true";
        try {
            mailStatus = mailerBean.sendMessage(this.getEmail());
            this.setStatus("Procesando... (refrescar para verificar nuevamente)");
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
        return response;
    }
}
