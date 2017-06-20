/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.connectors.trading.eis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 11 de junio de 2017 11:27:17 ART
 */
public class TradeExecServer implements Runnable {
    
    private Socket client;
    
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(4004);
        System.out.println("Servidor Trade escuchando sobre el puerto 4004.");
        
        while (true) {            
            Socket client = server.accept();
            Thread sthread = new Thread(new TradeExecServer(client));
            sthread.start();
        }
    }

    public TradeExecServer(Socket client) {
        this.client = client;
    }        

    @Override
    public void run() {
        String inline, outline;
        try {
            TradeProcessor tradeproc = new TradeProcessor();
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            InputStreamReader reader = new InputStreamReader(client.getInputStream());
            BufferedReader in = new BufferedReader(reader);
            
            System.out.println("Cliente conectado.");
            out.println(tradeproc.getGreeting());
            out.println(tradeproc.getReady());
            
            while ((inline = in.readLine()) != null) {                
                System.out.println("Recibido: " + inline);
                outline = tradeproc.processCommand(inline);
                System.out.println("Envía: " + outline);
                out.println(outline);
                if (outline.compareTo("BYE Cerrando conexion.") == 0) {
                    break;
                }
            }
            client.close();        
        } catch (IOException ex) {            
        }
    }
}
