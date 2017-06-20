/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.batch.webserverlog;

import com.nunait.glassfish.javaeetutorial.batch.webserverlog.items.LogLine;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import javax.batch.api.chunk.ItemReader;
import javax.batch.runtime.context.JobContext;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

/** 
 * Reads lines from the input log file
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 20 de junio de 2017 16:22:10 ART
 */
@Dependent
@Named("LogLineReader")
public class LogLineReader implements ItemReader {

    private ItemNumberCheckpoint checkpoint;
    private String fileName;
    private BufferedReader breader;
    
    @Inject    
    private JobContext jobCtx;

    public LogLineReader() {
    }
        
    @Override
    public void open(Serializable ckpt) throws Exception {        
        /* Use the checkpoint if this is a restart */
        if (ckpt == null) {
            checkpoint = new ItemNumberCheckpoint();
        } else {
            checkpoint = (ItemNumberCheckpoint) ckpt;
        }
        
        /* Read from the log file included with the application
         * (webserverlog/WEB-INF/classes/log1.txt) */
        fileName = jobCtx.getProperties().getProperty("log_file_name");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream iStream = classLoader.getResourceAsStream(fileName);
        breader = new BufferedReader(new InputStreamReader(iStream));
        
        /* Continue where we left off if this is a restart */
        for (int i = 0; i < checkpoint.getLineNum(); i++) {
            breader.readLine();
        }
    }

    @Override
    public void close() throws Exception {        
        breader.close();
    }

    @Override
    public Object readItem() throws Exception {        
        /* Return a LogLine object */
        String entry = breader.readLine();
        if (entry != null) {
            checkpoint.nextLine();
            return new LogLine(entry);
        } else {
            return null;
        }
    }

    @Override
    public Serializable checkpointInfo() throws Exception {        
        return checkpoint;
    }

}
