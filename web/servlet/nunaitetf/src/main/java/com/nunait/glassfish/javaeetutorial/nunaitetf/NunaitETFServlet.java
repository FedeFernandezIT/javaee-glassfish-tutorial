/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.nunaitetf;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 8 de mayo de 2017 10:18:14 ART
 */
@WebServlet(urlPatterns = {"/nunaitetf"}, asyncSupported = true)
public class NunaitETFServlet extends HttpServlet {

    private static final long serialVersionUID = 6324325271374394028L;
    private static final Logger logger = 
            Logger.getLogger(NunaitETFServlet.class.getSimpleName());
    private Queue<AsyncContext> requestQueue;
    @EJB private PriceVolumeBean pvbean;

    @Override
    public void init() throws ServletException {
        /* Cola para solicitudes */
        requestQueue = new ConcurrentLinkedQueue<>();
        /* Registra bean que actualiza precio y volumen */
        pvbean.registerServlet(this);
    }

    
    /* Método servidor */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        /* Poner la solicitud en modo asincrónico */
        final AsyncContext acontext = request.startAsync();
        /* Remover de la Cola cuando todo este hecho */
        acontext.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent event) throws IOException {
                requestQueue.remove(acontext);
                logger.log(Level.INFO, "Conexión cerrada.");
            }

            @Override
            public void onTimeout(AsyncEvent event) throws IOException {
                requestQueue.remove(acontext);
                logger.log(Level.INFO, "Conexión tiempo agotado.");
            }

            @Override
            public void onError(AsyncEvent event) throws IOException {
                requestQueue.remove(acontext);
                logger.log(Level.INFO, "Conexión falló.");
            }

            @Override
            public void onStartAsync(AsyncEvent event) throws IOException {                
            }
        });
        /* Agregar a la cola */
        requestQueue.add(acontext);
        logger.log(Level.INFO, "Conexión abierta.");
    }

    /* PriceVolumeBean invoca este método cada segundo para enviar actualizaciones */
    public void send(double price, int volume) {
        /* Enviar actualizaciones a todos los clientes conectados */
        for (AsyncContext acontext : requestQueue) {
            try {
                String msg = String.format("%.2f / %d", price, volume);
                PrintWriter writer = acontext.getResponse().getWriter();
                writer.write(msg);
                logger.log(Level.INFO, "Enviando: {0}", msg);
                /* Cerrar la conexión */
                /* El cliente (JavaScript) hace una nueva petición instantáneamente */
                acontext.complete();
            } catch(IOException ex) {
                logger.log(Level.INFO, ex.toString());
            }
        }
    }
        
}
