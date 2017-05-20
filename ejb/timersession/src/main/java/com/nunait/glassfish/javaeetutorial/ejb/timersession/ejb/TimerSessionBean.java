/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.ejb.timersession.ejb;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

/** 
 * TimerBran es un singleton session bean que crea un timer e imprime un
 * mensaje cuendo un timeout ocurre.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 19 de mayo de 2017 21:38:32 ART
 */
@Singleton
@Startup
public class TimerSessionBean {
    
    @Resource
    TimerService timerService;
    
    private Date lastProgrammaticTimeout;
    private Date lastAutomaticTimeout;
    
    private static final Logger logger =
            Logger.getLogger(TimerSessionBean.class.getCanonicalName());
    
    public void setTimer(long intervalDuration) {
        logger.log(Level.INFO,
                "Configurando un temporizador programado para {0} milisegundos a partir de ahora.",
                intervalDuration);
        Timer timer = timerService.createTimer(intervalDuration,
                "Nuevo temporizador programado creado.");
    }
    
    @Timeout
    public void programmaticTimeout(Timer timer) {
        this.setLastProgrammaticTimeout(new Date());
        logger.info("Timeout programado ocurrido.");
    }
    
    @Schedule(minute = "*/1", hour = "*", persistent = false)
    public void automaticTimeout() {
        this.setLastAutomaticTimeout(new Date());
        logger.info("Timeout automático ocurrido.");
    }

    /**     
     * @return  el último timeout programado
     */
    public String getLastProgrammaticTimeout() {
        if (lastProgrammaticTimeout != null) {
            return lastProgrammaticTimeout.toString();
        } else {
            return "never";
        }        
    }

    /**     
     * @param lastTimeout el timeout para establecer como último
     */
    public void setLastProgrammaticTimeout(Date lastTimeout) {
        this.lastProgrammaticTimeout = lastTimeout;
    }

    /**     
     * @return  el último timeout automático
     */
    public String getLastAutomaticTimeout() {
        if (lastAutomaticTimeout != null) {
            return lastAutomaticTimeout.toString();
        } else {
            return "never";
        }        
    }

    /**     
     * @param lastTimeout el timeout para establecer como último
     */
    public void setLastAutomaticTimeout(Date lastTimeout) {
        this.lastAutomaticTimeout = lastTimeout;
    }        
}
