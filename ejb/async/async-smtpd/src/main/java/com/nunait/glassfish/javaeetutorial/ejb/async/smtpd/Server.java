/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.ejb.async.smtpd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 20 de mayo de 2017 11:51:36 ART
 */
public class Server implements Runnable {
    
    private final Socket client;
    
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(3025);
        System.out.println("[Servidor de prueba SMTP escuchando en el puerto 3025]");
        
        while(true) {
            Socket client = server.accept();
            Thread sthread = new Thread(new Server(client));
            sthread.start();
        }
    }

    public Server(Socket client) {
        this.client = client;
    }        

    @Override
    public void run() {
        String inline;
        String msg = "";
        
        try {
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            InputStreamReader isr = new InputStreamReader(client.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            
            System.out.println("Client conectado.");
            out.println("220 com.nunait.glassfish.javaeetutorial.ejb.async.smtpd");
            inline = in.readLine();
            if (inline.split(" ")[0].compareTo("HELO") != 0 &&
                inline.split(" ")[0].compareTo("EHLO") != 0) {
                client.close(); return;
            }
            out.println("250 +OK Servidor SMTP Listo");
            inline = in.readLine();
            if (inline.split(":")[0].compareTo("MAIL FROM") != 0) {
                client.close(); return;
            }
            out.println("250 +OK Emisor OK");
            inline = in.readLine();
            if (inline.split(":")[0].compareTo("RCPT TO") != 0) {
                client.close(); return;
            }
            out.println("250 +OK Receptor OK");
            inline = in.readLine();
            if (!inline.contains("DATA")) {
                client.close(); return;
            }
            out.println("354 +OK Inicia entrada de correo.");
            
            while ((inline = in.readLine()) != null) {                
                if (inline.compareTo(".") == 0) {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {}
                    out.println("250 +OK Acción solicitada completa.");
                    in.readLine();
                    client.close();
                    System.out.println("[Entregando mensaje...]");
                    System.out.println(msg);
                    System.out.println("\n");
                } else {
                    msg = msg + inline + "\n";
                }
            }
        } catch (IOException ex) {}
    }
}
