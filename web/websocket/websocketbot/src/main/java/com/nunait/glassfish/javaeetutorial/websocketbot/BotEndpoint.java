/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.websocketbot;

import com.nunait.glassfish.javaeetutorial.websocketbot.decoders.MessageDecoder;
import com.nunait.glassfish.javaeetutorial.websocketbot.encoders.ChatMessageEncoder;
import com.nunait.glassfish.javaeetutorial.websocketbot.encoders.InfoMessageEncoder;
import com.nunait.glassfish.javaeetutorial.websocketbot.encoders.JoinMessageEncoder;
import com.nunait.glassfish.javaeetutorial.websocketbot.encoders.UsersMessageEncoder;
import com.nunait.glassfish.javaeetutorial.websocketbot.messages.ChatMessage;
import com.nunait.glassfish.javaeetutorial.websocketbot.messages.InfoMessage;
import com.nunait.glassfish.javaeetutorial.websocketbot.messages.JoinMessage;
import com.nunait.glassfish.javaeetutorial.websocketbot.messages.Message;
import com.nunait.glassfish.javaeetutorial.websocketbot.messages.UsersMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/** 
 * Websocket endpoint. Hay una instancia por conexión.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 11 de mayo de 2017 12:11:26 ART
 */
@ServerEndpoint(
        value = "/websocketbot",
        decoders = {MessageDecoder.class},
        encoders = {JoinMessageEncoder.class, ChatMessageEncoder.class,
                    InfoMessageEncoder.class, UsersMessageEncoder.class}
)
public class BotEndpoint {
    
    private static final Logger LOGGER = 
            Logger.getLogger(BotEndpoint.class.getSimpleName());
    
    /* Bean para la funcionalidad Bot. */
    @Inject
    private BotBean botbean;
    /* Servicio Executor para procesamiento asincrónico */
    @Resource(name = "comp/DefualtManagerExecutorService")
    private ManagedExecutorService mes;
    
    @OnOpen
    public void openConnection(Session session) {
        LOGGER.log(Level.INFO, "Conexión abierta.");
    }
    
    @OnMessage
    public void message(final Session session, Message msg) {
        LOGGER.log(Level.INFO, "Recibido: {0}", msg.toString());
        
        if (msg instanceof JoinMessage) {
            /* Agrega un nuevo usuario y notifica a todos */
            JoinMessage jmsg = (JoinMessage) msg;
            session.getUserProperties().put("name", jmsg.getName());
            session.getUserProperties().put("active", true);
            LOGGER.log(Level.INFO, "Recibido: {0}", jmsg.toString());
            sendAll(session, new InfoMessage(jmsg.getName() + 
                    " ha sido unido al chat."));
            sendAll(session, new ChatMessage("NunaIT", jmsg.getName(), 
                    "Hola, bienvenido!!"));
            sendAll(session, new UsersMessage(this.getUserList(session)));
        } else if (msg instanceof ChatMessage) {
            /* Envía el mensaje a todos los conectados */
            final ChatMessage cmsg = (ChatMessage) msg;
            LOGGER.log(Level.INFO, "Recibido: {0}", cmsg.toString());
            sendAll(session, cmsg);
            if (cmsg.getTarget().compareTo("NunaIT") == 0) {
                /* El agente Bot responde el mensaje */
                mes.submit(new Runnable() {
                    @Override
                    public void run() {
                        String resp = botbean.respond(cmsg.getMessage());
                        sendAll(session, new ChatMessage(
                                "NunaIT", cmsg.getName(), resp));
                    }
                });
            }
        }
    }
    
    @OnClose
    public void closedConnection(Session session) {
        /* Notifica a todos los usuarios que estaban conectados */
        session.getUserProperties().put("active", false);
        if (session.getUserProperties().containsKey("name")) {
            String name = session.getUserProperties().get("name").toString();
            sendAll(session, new InfoMessage(name + "ha dejado el chat"));
            sendAll(session, new UsersMessage(this.getUserList(session)));
        }
        LOGGER.log(Level.INFO, "Conexión cerrada.");
    }
    
    @OnError
    public void error(Session session, Throwable t) {
        LOGGER.log(Level.INFO, "Conexión fallida ({0})", t.toString());
    }
    
    /* Envía un mensaje a todos los clientes conectados
     * El endpoint resoluciona que encoder utilizar según el tipo
     * de mansaje */
    private synchronized void sendAll(Session session, Message msg) {
        try {
            for (Session s : session.getOpenSessions()) {
                s.getBasicRemote().sendObject(msg);
                LOGGER.log(Level.INFO, "Enviando: {0}", msg.toString());
            }
        } catch (IOException | EncodeException e) {
            LOGGER.log(Level.INFO, e.toString());
        }
    }
    
    /* Retorna la lista de usuarios desde las propiedades de todas las
     * sessiones abiertas */
    private List<String> getUserList(Session session) {
        List<String> users = new ArrayList<>();
        users.add("NunaIT");
        for (Session s : session.getOpenSessions()) {
            if (s.isOpen() && (boolean) s.getUserProperties().get("active"))
                users.add(s.getUserProperties().get("name").toString());            
        }
        return users;
    }
}
