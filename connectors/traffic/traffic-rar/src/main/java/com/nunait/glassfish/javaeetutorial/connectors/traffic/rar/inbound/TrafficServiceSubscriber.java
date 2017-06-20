/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.connectors.traffic.rar.inbound;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import javax.resource.ResourceException;
import javax.resource.spi.endpoint.MessageEndpoint;
import javax.resource.spi.work.Work;

/** 
 * The RA runs this class to connect to the traffic information system
 * EIS and invoke methods on TrafficMdb
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 11 de junio de 2017 23:14:51 ART
 */
public class TrafficServiceSubscriber implements Work {
    private static final Logger log = Logger.getLogger("TrafficServiceSocket");
    
    private MessageEndpoint mdb;
    private TrafficActivationSpec spec;
    private Socket socket;
    private volatile boolean  listen;

    public TrafficServiceSubscriber(TrafficActivationSpec spec, MessageEndpoint mdb) {
        this.mdb = mdb;
        this.spec = spec;        
        this.listen = true;
    }                

    @Override
    public void run() {
        BufferedReader in;
        String jsonLine;
        String key;
        JsonParser parser;
        
        try {
            /* Connect to the traffic EIS */
            int port = Integer.parseInt(spec.getPort());
            log.info("[TrafficServiceSubscriber] Connecting...");
            socket = new Socket("localhost", port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            log.info("[TrafficServiceSubscriber] Connected");
            
            while (listen) {                
                jsonLine = in.readLine();
                parser = Json.createParser(new StringReader(jsonLine));
                if (parser.hasNext() && parser.next() == Event.START_OBJECT && 
                    parser.hasNext() && parser.next() == Event.KEY_NAME) {
                    
                    key = parser.getString();
                    /* Does the MDB support this message? */
                    if (spec.getCommands().containsKey(key)) {
                        Method mdbMethod = spec.getCommands().get(key);
                        /* Invoke the method of the MDB */
                        callMdb(mdb, mdbMethod, jsonLine);
                    } else
                        log.info("[TrafficServerSubscriber] Unknown message");                    
                } else
                    log.info("[TrafficServiceSubscriber] Wrong message format");
            }
        } catch (IOException | ResourceException ex) {
            log.log(Level.INFO, "[TrafficServiceSubscriber] Error - {0}", ex.getMessage());
        }
    }
    
    @Override
    public void release() {
        log.info("[TrafficServiceSubscriber] release()");
        try {
            listen = false;
            socket.close();
        } catch (IOException ex) {            
        }
    }

    /* Invoke a method from the MDB */
    private String callMdb(MessageEndpoint mdb, Method command, String... params) throws ResourceException {
        String response;
        try {
            log.info("[TrafficServiceSubscriber] callMdb()");
            mdb.beforeDelivery(command);
            Object ret = command.invoke(mdb, (Object[]) params);
            response = (String) ret;
        } catch (NoSuchMethodException | IllegalAccessException |
                 IllegalArgumentException | InvocationTargetException ex) {
            log.info(String.format("Invocation error %s", ex.getMessage()));
            response = "ERROR Invocation error - " + ex.getMessage();
        }
        mdb.afterDelivery();
        return response;
    }

}
