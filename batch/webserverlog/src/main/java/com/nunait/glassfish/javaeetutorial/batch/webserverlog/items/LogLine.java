/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.batch.webserverlog.items;

/** 
 * Represents a log line in the filtered log file
 * Used as output items in the ItemWriter implementation
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 20 de junio de 2017 15:53:13 ART
 */
public class LogLine {
    
    private final String datetime;    
    private final String ipaddr;
    private final String browser;
    private final String url;
    
    /* Construct the line from individual items */
    public LogLine(String datetime, String ipaddr, String browser, String url) {
        this.datetime = datetime;
        this.ipaddr = ipaddr;
        this.browser = browser;
        this.url = url;
    }
    
    /* Construct an item from a log line */
    public LogLine(String line) {
        //System.out.println(line);
        String[] result = line.split(", ");        
        this.datetime = result[0];
        this.ipaddr = result[1];
        this.browser = result[2];
        this.url = result[3];
    }
    
    /* For logging purposes */
    @Override
    public String toString() {
        return datetime + " " + ipaddr + " " + browser + " " + url;
    }
    
    /* Getters and setters */
    public String getDatetime() {
        return datetime;
    }

    public String getIpaddr() {
        return ipaddr;
    }

    public String getBrowser() {
        return browser;
    }

    public String getUrl() {
        return url;
    }
    
}
