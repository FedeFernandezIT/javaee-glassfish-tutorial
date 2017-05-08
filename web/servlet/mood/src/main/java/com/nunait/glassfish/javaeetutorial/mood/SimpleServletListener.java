/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.mood;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/** 
 * Listener del ciclo de vida de una Aplicación Web
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 7 de mayo de 2017 20:15:18 ART
 */
@WebListener()
public class SimpleServletListener implements ServletContextListener,
        ServletContextAttributeListener {

    static final Logger log = Logger.getLogger(SimpleServletListener.class.getName());
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("[servlet-mood] Contexto incicializado");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("[servlet-mood] Contexto destruído");
    }

    @Override
    public void attributeAdded(ServletContextAttributeEvent event) {
        log.log(Level.INFO,
                "[servlet-mood] Atributo {0} ha sido agregado, con el valor: {1}",
                new Object[] {event.getName(), event.getValue()});
    }

    @Override
    public void attributeRemoved(ServletContextAttributeEvent event) {
        log.log(Level.INFO, 
                "[servlet-mood] Atributo {0} ha sido removido", 
                event.getName());                
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent event) {
        log.log(Level.INFO, 
                "[servlet-mood] Atributo {0} ha sido reemplazado, con el valor: {1}",
                new Object[] {event.getName(), event.getValue()});
    }

}
