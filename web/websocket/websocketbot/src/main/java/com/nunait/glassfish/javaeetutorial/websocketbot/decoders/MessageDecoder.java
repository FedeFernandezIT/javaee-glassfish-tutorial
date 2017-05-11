/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.websocketbot.decoders;

import com.nunait.glassfish.javaeetutorial.websocketbot.messages.ChatMessage;
import com.nunait.glassfish.javaeetutorial.websocketbot.messages.JoinMessage;
import com.nunait.glassfish.javaeetutorial.websocketbot.messages.Message;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/** 
 * Decodifica un mensaje JSON como un JoinMassage o ChatMessage.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 11 de mayo de 2017 10:51:09 ART
 */
public class MessageDecoder implements Decoder.Text<Message> {    
    /* Guarda las duplas name-value del JSON como un Map */
    
    private Map<String, String> messageMap;
    
    /* Crea un nuevo objeto Message si el mensaje JSON puede ser decodificado */
    @Override
    public Message decode(String jsonstr) throws DecodeException {
        Message msg = null;
        if (willDecode(jsonstr)) {
            switch (messageMap.get("type")) {
                case "join":
                    msg = new JoinMessage(messageMap.get("name"));
                    break;
                case "chat":
                    msg = new ChatMessage(
                            messageMap.get("name"), 
                            messageMap.get("target"),
                            messageMap.get("message"));
                    break;
            }
        } else {
            throw new DecodeException(jsonstr, 
                    "[Message] No puede ser decodificado.");
        }
        return  msg;
    }

    /* Decodifica un mensaje JSON en un Map y verrifica que este contenga
     * todos los campos requeridos de acuerdo a su tipo. */
    @Override
    public boolean willDecode(String jsonstr) {
        boolean decodes = false;
        /* Covierte el mensaje JSON en un Map */
        messageMap = new HashMap<>();
        JsonParser parser = Json.createParser(new StringReader(jsonstr));
        while (parser.hasNext()) {
            if (parser.next() == JsonParser.Event.KEY_NAME) {
                String key = parser.getString();
                parser.next();
                String value = parser.getString();
                messageMap.put(key, value);
            }                        
        }
        
        /* Verificar el tipo de mensaje y si contiene todos los campos
         * requeridos */
        Set keys = messageMap.keySet();
        if (keys.contains("type")) {
            switch (messageMap.get("type")) {
                case "join":
                    if (keys.contains("name"))
                        decodes = true;                    
                    break;
                case "chat":
                    String[] chatMsgKeys = {"name", "target", "message"};
                    if (keys.containsAll(Arrays.asList(chatMsgKeys)))
                        decodes = true;
                    break;
            }
        }
        return decodes;
    }

    @Override
    public void init(EndpointConfig config) {}

    @Override
    public void destroy() {}

}
