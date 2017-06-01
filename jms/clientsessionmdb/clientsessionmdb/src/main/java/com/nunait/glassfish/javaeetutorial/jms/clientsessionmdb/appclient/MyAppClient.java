/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.jms.clientsessionmdb.appclient;

import com.nunait.glassfish.javaeetutorial.jms.clientsessionmdb.sb.PublisherRemote;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;

/** 
 * MyAppClient es el programa cliente de esta aplicación. Este invoca dos veces
 * al método publishNews de Publisher.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 1 de junio de 2017 11:05:43 ART
 */
public class MyAppClient {
    public static final Logger logger = Logger.getLogger("MyAppClient");
    
    @EJB
    private static PublisherRemote publisher;

    public MyAppClient(String args[]) {
    }        
    
    public static void main(String[] args) {
        MyAppClient client = new MyAppClient(args);
        client.doTest();
        System.exit(0);
    }
    
    public void doTest() {
        try {
            publisher.publisherNews();
            publisher.publisherNews();
            System.out.println("Para ver la salida del bean,");
            System.out.println("chequear <install_dir>/domains/domain1/logs/server.log.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Excepción: {0}", e.toString());
        }
    }
}
