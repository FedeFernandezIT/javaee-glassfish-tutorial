/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.batch.webserverlog;

import com.nunait.glassfish.javaeetutorial.batch.webserverlog.items.LogFilteredLine;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.List;
import javax.batch.api.chunk.ItemWriter;
import javax.batch.runtime.context.JobContext;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

/** 
 * Write the filtered items.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 20 de junio de 2017 17:03:03 ART
 */
@Dependent
@Named("LogFilteredLineWriter")
public class LogFilteredLineWriter implements ItemWriter {
    
    private String fileName;
    private BufferedWriter bwriter;
    
    @Inject
    private JobContext jobCtx;
    
    public LogFilteredLineWriter() {
    }
    
    @Override
    public void open(Serializable ckpt) throws Exception {        
        fileName = jobCtx.getProperties().getProperty("filtered_file_name");
        /* If the job was restarted, continue writing at the end of the file.
         * Otherwise, overwrite the file. */
        if (ckpt != null) {
            bwriter = new BufferedWriter(new FileWriter(fileName, true));
        } else {
            bwriter = new BufferedWriter(new FileWriter(fileName, false));
        }
    }

    @Override
    public void close() throws Exception {        
        bwriter.close();
    }

    @Override
    public void writeItems(List<Object> items) throws Exception {        
        /* Write the filtered lines to the output file */
        for (int i = 0; i < items.size(); i++) {
            LogFilteredLine filtLine = (LogFilteredLine) items.get(i);
            bwriter.write(filtLine.toString());
            bwriter.newLine();
        }
    }

    @Override
    public Serializable checkpointInfo() throws Exception {        
        return new ItemNumberCheckpoint();
    }

}
