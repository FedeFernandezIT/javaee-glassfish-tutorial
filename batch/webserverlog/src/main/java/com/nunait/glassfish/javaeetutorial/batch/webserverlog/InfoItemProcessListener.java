/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.batch.webserverlog;

import com.nunait.glassfish.javaeetutorial.batch.webserverlog.items.LogLine;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.batch.api.chunk.listener.ItemProcessListener;
import javax.enterprise.context.Dependent;
import javax.inject.Named;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 20 de junio de 2017 17:43:15 ART
 */
@Dependent
@Named("InfoItemProcessListener")
public class InfoItemProcessListener implements ItemProcessListener {
    private static final Logger logger = 
            Logger.getLogger("InfoItemProcessListener");

    public InfoItemProcessListener() {
    }
        
    @Override
    public void beforeProcess(Object item) throws Exception {
        LogLine logLine = (LogLine) item;
        logger.log(Level.INFO, "Processing entry {0}", logLine);
    }

    @Override
    public void afterProcess(Object item, Object result) throws Exception {
    }

    @Override
    public void onProcessError(Object item, Exception ex) throws Exception {
        LogLine logLine = (LogLine) item;
        logger.log(Level.WARNING, "Error processing entry {0}", logLine);
    }

}
