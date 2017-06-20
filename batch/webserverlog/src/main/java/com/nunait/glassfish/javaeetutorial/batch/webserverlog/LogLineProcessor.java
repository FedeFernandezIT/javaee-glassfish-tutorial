/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.batch.webserverlog;

import com.nunait.glassfish.javaeetutorial.batch.webserverlog.items.LogFilteredLine;
import com.nunait.glassfish.javaeetutorial.batch.webserverlog.items.LogLine;
import java.util.Properties;
import javax.batch.api.chunk.ItemProcessor;
import javax.batch.runtime.context.JobContext;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

/** 
 * Processes items from the log file
 * Filters only those items from mobile or tablet browsers,
 * depending on the properties specified in the job definition file.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 20 de junio de 2017 16:50:17 ART
 */
@Dependent
@Named("LogLineProcessor")
public class LogLineProcessor implements ItemProcessor {
    
    private String[] browsers;
    private int nbrowsers = 0;
    @Inject
    private JobContext jobCtx;

    public LogLineProcessor() {
    }

    @Override
    public Object processItem(Object item) throws Exception {
        /* Obtain a list of browsers we are interested in */
        if (nbrowsers == 0) {
            Properties props = jobCtx.getProperties();
            nbrowsers = Integer.parseInt(props.getProperty("num_browsers"));
            browsers = new String[nbrowsers];
            for (int i = 1; i < nbrowsers; i++) {
                browsers[i - 1] = props.getProperty("browser_" + i);
            }
        }
        
        LogLine logLine = (LogLine) item;
        /* Filter for only the mobile/tablet browsers as specified */
        for (int i = 0; i < nbrowsers; i++) {
            if (logLine.getBrowser().equals(browsers[i])) {
                /* The new items have fewer fields */
                return new LogFilteredLine(logLine);
            }
        }
        return null;
    }
        
}
