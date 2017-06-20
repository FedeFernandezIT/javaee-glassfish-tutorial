/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.batch.webserverlog.items;

/** 
 * Represents a log line in the filtered log file
 * Used as output items in the ItemWriter implementation.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 20 de junio de 2017 16:01:34 ART
 */
public class LogFilteredLine {
    
    private final String ipaddr;
    private final String url;
    
    /* Construct from an input log line */
    public LogFilteredLine(LogLine ll) {
        this.ipaddr = ll.getIpaddr();
        this.url = ll.getUrl();
    }
    
    /* Construct from an output log line */
    public LogFilteredLine(String line) {
        String[] result = line.split(", ");
        this.ipaddr = result[0];
        this.url = result[1];
    }

    @Override
    public String toString() {
        return ipaddr + ",  " + url;
    }
    
    
}
