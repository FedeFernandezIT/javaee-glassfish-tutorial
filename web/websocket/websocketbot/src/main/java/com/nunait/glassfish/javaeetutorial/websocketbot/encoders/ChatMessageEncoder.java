/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nunait.glassfish.javaeetutorial.websocketbot.encoders;

import com.nunait.glassfish.javaeetutorial.websocketbot.messages.ChatMessage;
import java.io.StringWriter;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * Codifica un ChatMessage como un JSON.
 *
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 11 de mayo de 2017 10:16:14 ART
 */
public class ChatMessageEncoder implements Encoder.Text<ChatMessage> {

    @Override
    public String encode(ChatMessage chatMessage) throws EncodeException {
        StringWriter swriter = new StringWriter();
        try (JsonGenerator jsonGen = Json.createGenerator(swriter)) {
            jsonGen.writeStartObject()
                    .write("type", "chat")
                    .write("name", chatMessage.getName())
                    .write("target", chatMessage.getTarget())
                    .write("message", chatMessage.getMessage())
                .writeEnd();
        }
        return swriter.toString();
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

}
