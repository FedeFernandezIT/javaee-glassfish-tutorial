/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.nunaitetf;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

/** 
 *  Actualiza información de precio y volumen cada segundo.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 8 de mayo de 2017 10:20:27 ART
 */
@Startup
@Singleton
public class PriceVolumeBean {
    
    /* Uso del servicio temporizador del contenedor */
    @Resource TimerService tservice;
    private NunaitETFServlet servlet;
    private Random random;
    private volatile double price = 100.0;
    private volatile int volume = 300000;
    
    private static final Logger logger = 
            Logger.getLogger(PriceVolumeBean.class.getSimpleName());
    
    @PostConstruct
    public void Init() {
        /* Incicializa el EJB y crea un temporizador */
        logger.log(Level.INFO, "Inciando EJB.");
        random = new Random();
        servlet = null;
        tservice.createIntervalTimer(1000, 1000, new TimerConfig());
    }
    
    public void registerServlet(NunaitETFServlet servlet) {
        /* Asocia un servlet para enviar actualizaciones a este */
        this.servlet = servlet;
    }
    
    @Timeout
    public void timeout() {
        /* Ajusta precio y volumen y envía actualizaciones */
        price += 1.0 * (random.nextInt(100)-50) / 100.0;
        volume += random.nextInt(5000) - 2500;
        if (servlet != null) {
            servlet.send(price, volume);
        }
    }
}
