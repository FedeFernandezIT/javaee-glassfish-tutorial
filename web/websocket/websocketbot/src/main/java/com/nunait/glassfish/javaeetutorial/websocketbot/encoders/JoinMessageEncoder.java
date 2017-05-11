/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nunait.glassfish.javaeetutorial.websocketbot.encoders;

import com.nunait.glassfish.javaeetutorial.websocketbot.messages.JoinMessage;
import java.io.StringWriter;
import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/** 
 * Codifica un JoinMessage como un JSON.
 * 
 * @author Federico Fernandez | fede.fernandez.it@gmail.com
 * @created 11 de mayo de 2017 10:38:28 ART
 */
public class JoinMessageEncoder implements Encoder.Text<JoinMessage>{

    @Override
    public String encode(JoinMessage joinMessage) throws EncodeException {
        StringWriter swriter = new StringWriter();
        try (JsonGenerator jsonGen = Json.createGenerator(swriter)) {
            jsonGen.writeStartObject()
                    .write("type", "join")
                    .write("name", joinMessage.getName())
                .writeEnd();
        }
        return swriter.toString();
    }

    @Override
    public void init(EndpointConfig config) {}

    @Override
    public void destroy() {}
        
}
