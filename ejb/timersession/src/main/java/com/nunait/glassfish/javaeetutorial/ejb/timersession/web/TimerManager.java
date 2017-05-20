/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.ejb.timersession.web;

import com.nunait.glassfish.javaeetutorial.ejb.timersession.ejb.TimerSessionBean;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/** 
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 19 de mayo de 2017 22:32:15 ART
 */
@Named
@SessionScoped
public class TimerManager implements Serializable {
    private static final long serialVersionUID = 1105815871423927882L;
        
    @EJB
    private TimerSessionBean timerSession;
    
    private String lastProgrammaticTimeout;
    private String lastAutomaticTimeout;

    public TimerManager() {
        this.lastProgrammaticTimeout = "never";
        this.lastAutomaticTimeout = "never";        
    }

    public void setTimer() {
        long timeoutDuration = 8000;
        timerSession.setTimer(timeoutDuration);
    }
    
    public String getLastProgrammaticTimeout() {
        lastProgrammaticTimeout = timerSession.getLastProgrammaticTimeout();
        return lastProgrammaticTimeout;
    }

    public void setLastProgrammaticTimeout(String lastTimeout) {
        this.lastProgrammaticTimeout = lastTimeout;
    }

    public String getLastAutomaticTimeout() {
        lastAutomaticTimeout = timerSession.getLastAutomaticTimeout();
        return lastAutomaticTimeout;
    }

    public void setLastAutomaticTimeout(String lastTimeout) {
        this.lastAutomaticTimeout = lastTimeout;
    }        
}
