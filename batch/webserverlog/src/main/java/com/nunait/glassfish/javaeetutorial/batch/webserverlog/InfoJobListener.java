/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.batch.webserverlog;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.batch.api.listener.JobListener;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 20 de junio de 2017 17:39:46 ART
 */
@Dependent
@Named("InfoJobListener")
public class InfoJobListener implements JobListener {
    private static final Logger logger = Logger.getLogger("InfoJobListener");

    public InfoJobListener() {
    }
        
    @Override
    public void beforeJob() throws Exception {
        logger.log(Level.INFO, "The job is starting");
    }

    @Override
    public void afterJob() throws Exception {
        logger.log(Level.INFO, "The job has finished.");
    }
    
}
