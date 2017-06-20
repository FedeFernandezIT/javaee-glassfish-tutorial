package com.nunait.glassfish.javaeetutorial.connectors.traffic.eis;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 11 de junio de 2017 21:05:42 ART
 */
public class TrafficServer {
    
    private static List<Socket> clients;
    
    public static void main(String[] args) throws IOException {
        
        clients = new ArrayList<>();
        final ServerSocket server = new ServerSocket(4008);
        System.out.println("Traffic EIS aceptando conexiones sobre el puerto 4008");
        
        /* Accept connections */
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Socket client = server.accept();
                        synchronized(TrafficServer.class) {
                            clients.add(client);
                        }
                        System.out.println("Cliente conectado.");
                    } catch (IOException ex) {                        
                    }
                }
            }
        }).start();
        
        /* Send traffic information to all connected peers */
        PrintWriter out;
        String report;
        TrafficService tsvc = new TrafficService();
        while (true) {            
            report = tsvc.getReport();
            for (Socket client : clients) {
                out = new PrintWriter(client.getOutputStream(), true);
                //System.out.println(report);
                out.println(report);
            }
            
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
        }
    }
}
